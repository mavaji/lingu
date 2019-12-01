package ganjoor.service;

import ganjoor.dao.CategoryDao;
import ganjoor.dao.PoemDao;
import ganjoor.dao.PoetDao;
import ganjoor.dao.VerseDao;
import parsmorph.service.Possibility;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vahid
 * Date: 4/18/11
 * Time: 6:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class GanjoorAnalyzer {
    private CategoryDao categoryDao;

    private PoemDao poemDao;

    private PoetDao poetDao;

    private VerseDao verseDao;

    public List<Possibility> analyze(String text) {
        return null;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void setPoemDao(PoemDao poemDao) {
        this.poemDao = poemDao;
    }

    public void setPoetDao(PoetDao poetDao) {
        this.poetDao = poetDao;
    }

    public void setVerseDao(VerseDao verseDao) {
        this.verseDao = verseDao;
    }
}
