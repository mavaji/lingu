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
import parsmorph.model.Affix;
import parsmorph.model.AffixAUX;

import java.util.List;

/**
 * @author Vahid Mavaji
 */
public class AffixAUXDao extends BaseDao {
    public Affix[] getAuxBeginnings() {
        List<Affix> affixes = findByNamedQuery(AffixAUX.GET_AUX_BEGINNINGS);
        return affixes.toArray(new Affix[0]);
    }

    public Affix[] getAuxEndings() {
        List<Affix> affixes = findByNamedQuery(AffixAUX.GET_AUX_ENDINGS);
        return affixes.toArray(new Affix[0]);
    }
}
