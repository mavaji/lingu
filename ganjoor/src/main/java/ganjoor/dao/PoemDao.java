package ganjoor.dao;

import core.dao.BaseDao;
import ganjoor.model.Poem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vahid Mavaji
 */
public class PoemDao extends BaseDao {
    public Poem[] getByPoetId(Long poetId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("poetId", poetId);
        List<Poem> poems = findByNamedQuery(Poem.FIND_BY_POET_ID, params);
        return poems.toArray(new Poem[0]);
    }
}
