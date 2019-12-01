package ganjoor.dao;

import core.dao.BaseDao;
import ganjoor.model.Verse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vahid Mavaji
 */
public class VerseDao extends BaseDao {

    public Verse[] getByPoemId(Long poemId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("poemId", poemId);
        List<Verse> verses = findByNamedQuery(Verse.FIND_BY_POEM_ID, params);
        return verses.toArray(new Verse[0]);
    }

    public Verse[] getByText(String text) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("test", text);
        List<Verse> verses = findByNamedQuery(Verse.FIND_BY_TEXT, params);
        return verses.toArray(new Verse[0]);
    }

    public Verse[] getByPoetAndText(Long poetId, String text) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("poetId", poetId);
        params.put("text", text);
        List<Verse> verses = findByNamedQuery(Verse.FIND_BY_POET_ID_AND_TEXT, params);
        return verses.toArray(new Verse[0]);
    }

    public Verse[] getByCenturyAndText(Long century, String text) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("century", century);
        params.put("text", text);
        List<Verse> verses = findByNamedQuery(Verse.FIND_BY_CENTURY_AND_TEXT, params);
        return verses.toArray(new Verse[0]);
    }

    public Long countByCentury(Long century) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("century", century);
        return (Long) findByNamedQuery(Verse.COUNT_BY_CENTURY, params).get(0);
    }

    public Verse[] getByPoetsAndText(Long[] poetIds, String text) {
        if (poetIds.length == 0) {
            return getByText(text);
        }

        String query = "from Verse v where v.text like '%" + text + "%' and (";

        for (Long poetId : poetIds) {
            query += " v.poem.category.poet.id=" + poetId;
            query += " or ";
        }

        query += "1=0)";

        List<Verse> verses = find(query);
        return verses.toArray(new Verse[0]);
    }
}
