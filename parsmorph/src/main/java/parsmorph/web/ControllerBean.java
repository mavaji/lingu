/*
 * Copyright 2012 Vahid Mavaji
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package parsmorph.web;

import core.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import parsmorph.model.Comment;
import parsmorph.model.Query;
import parsmorph.service.MorphologicalAnalyser;
import parsmorph.service.PhonemeAnalyzer;
import parsmorph.service.Possibility;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vahid Mavaji
 */
@ManagedBean(name = "controllerBean")
@RequestScoped
public class ControllerBean {
    private int activeIndex = 0;

    private String phonemeQuery;
    private String[] phonemeResults;

    private String query;
    private String name;
    private String email;
    private String comment;

    private Possibility[] possibilities;

    private MorphologicalAnalyser analyser = new MorphologicalAnalyser();
    private PhonemeAnalyzer phonemeAnalyzer = new PhonemeAnalyzer();

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public String getPhonemeQuery() {
        return phonemeQuery;
    }

    public void setPhonemeQuery(String phonemeQuery) {
        this.phonemeQuery = phonemeQuery;
    }

    public String[] getPhonemeResults() {
        return phonemeResults;
    }

    public void setPhonemeResults(String[] phonemeResults) {
        this.phonemeResults = phonemeResults;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Possibility[] getPossibilities() {
        return possibilities;
    }

    public void setPossibilities(Possibility[] possibilities) {
        this.possibilities = possibilities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String handlePhonemes() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        String[] results = phonemeAnalyzer.textToPhoneme(this.phonemeQuery);
        if (results.length > 0) {
            phonemeResults = results;
            savePhonology(request, phonemeQuery, phonemeResults);
        } else {
            phonemeResults = null;
        }
        this.activeIndex = 1;
        return "success";
    }

    public String handleComments() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        if (name == null || name.equals("")) {
            name = "anonymous";
        }

        if (comment == null || comment.equals("") || checkInjection(comment)) {
            return "success";
        }

        Comment c = new Comment(name, email, comment, request.getRemoteAddr());
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        session.save(c);

        tx.commit();
        HibernateUtil.shutdown();

        name = "";
        email = "";
        comment = "";
        return "success";
    }

    public String handleQueries() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if (query.length() == 0) {
            possibilities = null;
        } else {
            String morpheme = query;

            if (checkInjection(morpheme)) {
                return "";
            }

            possibilities = analyser.analyse(morpheme);
            for (Possibility possibility : possibilities) {
                possibility.buildPersianTreeNode(morpheme);
                possibility.buildLatinTreeNode(morpheme);
            }

            saveQuery(request, morpheme);
        }
        return "success";
    }

    private void savePhonology(HttpServletRequest request, String phonemeQuery, String[] phonemeResults) {
        String result = "";
        for (String s : phonemeResults) {
            result += s + "\n";
        }

        Query query = new Query(phonemeQuery, request.getRemoteAddr(), result, true);
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        session.save(query);

        tx.commit();
        HibernateUtil.shutdown();
    }

    private void saveQuery(HttpServletRequest request, String morpheme) {
        String result = toStr(possibilities);

        Query query = new Query(morpheme, request.getRemoteAddr(), result);
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        session.save(query);

        tx.commit();
        HibernateUtil.shutdown();
    }

    private String toStr(Possibility[] possibilities) {
        String result = "None";

        if (possibilities != null) {
            result = "[";
            for (Possibility possibility : possibilities) {
                result += "(" + possibility + ")";
            }
            result += "]";
        }

        if (result.equals("[]")) {
            result = "None";
        }
        return result;
    }

    private boolean checkSqlInjection(String text) {
        Pattern p = Pattern.compile("/(\\%27)|(\\')|(\\-\\-)|(\\%23)|(#)/ix");
        Matcher m = p.matcher(text);
        return m.find();
    }

    private boolean checkCssInjection(String text) {
        Pattern p = Pattern.compile("(\\%3C|<|\\%3E|>|\\%5B|\\[|\\%5E|\\])+");
        Matcher m = p.matcher(text);
        return m.find();
    }

    private boolean checkInjection(String text) {
        return checkSqlInjection(text) || checkCssInjection(text);
    }
}
