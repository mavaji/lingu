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

package parsmorph.dao;

import core.dao.BaseDao;
import parsmorph.model.Entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vahid Mavaji
 */
public class EntryDao extends BaseDao {
    public Entry[] getByWrittenForm(String writtenForm) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("writtenForm", writtenForm);
        List<Entry> entries = findByNamedQuery(Entry.GET_BY_WRITTEN_FORM, params);
        return entries.toArray(new Entry[0]);
    }
}