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
import parsmorph.model.AffixINFL;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vahid Mavaji
 */
public class AffixINFLDao extends BaseDao {
    public Affix[] getByAffixType(String[] affixTypeCodes) {
        List<Affix> affixes = findByNamedQuery(AffixINFL.GET_ALL_AFFIX_INFL);

        List<Affix> result = new ArrayList<Affix>();
        for (Affix affix : affixes) {
            if (affix.getAffixType() != null && ArrayUtils.contains(affixTypeCodes, affix.getAffixType().getCode())) {
                result.add(affix);
            }
        }

        return result.toArray(new Affix[0]);
    }
}
