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

import core.util.Author;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vahid Mavaji
 */
@ManagedBean(name = "authorBean")
@RequestScoped
public class AuthorBean implements Serializable {

    private List<Author> authors;

    private Author selectedAuthors;

    public AuthorBean() {
        authors = new ArrayList<Author>();

        Author vahid = new Author("Vahid Mavaji", "vahid.jpg", "http://mavaji.com");
        vahid.setDescription("Vahid Mavaji received his B.Sc. in Computer Engineering from Iran University of" +
                " Science and Technology in 2004, and his M.Sc. in the same field from Sharif University of Technology in 2006. " +
                "Since 2010, he is a graduate student in Computational Linguistics at Sharif University of Technology. " +
                "His main interests include: Linguistics, Software Engineering, and Java Programming.");
        authors.add(vahid);

        Author eslami = new Author("Dr. Moharram Eslami", "eslami.jpg", "http://language.sharif.edu/files/CV.Eslami.pdf");
        eslami.setDescription("Moharram Eslami received his Ph.D. in Linguistics from " +
                "University of Tehran in 2000. He is a faculty member of the Division for Computational Linguistics" +
                " in Languages and Linsguistics Department, Sharif University of Technology and an assistant professor in Zanjan University." +
                "His main interests include General Linguistics, Phonetics, Phonology, Persian Language in general," +
                "Natural Language Processing (NLP), Text-to-Speech Synthesis (TTS). ");
        authors.add(eslami);
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public Author getSelectedAuthors() {
        return selectedAuthors;
    }

    public void setSelectedAuthors(Author selectedAuthors) {
        this.selectedAuthors = selectedAuthors;
    }
}
