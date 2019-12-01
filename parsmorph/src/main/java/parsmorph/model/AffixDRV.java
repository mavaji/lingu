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

package parsmorph.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Vahid Mavaji
 */
@Entity
@Table(name = "FL_AFFIX_DRV")
@NamedQueries({
        @NamedQuery(name = AffixDRV.GET_SUFFIXES, query = "from AffixDRV a where a.position=1 order by a.affixLen desc "),
        @NamedQuery(name = AffixDRV.GET_PREFIXES, query = "from AffixDRV a where a.position=-1 order by a.affixLen desc ")
})
public class AffixDRV extends Affix {

    public static final String GET_SUFFIXES = "get_suffixes";
    public static final String GET_PREFIXES = "get_prefixes";


}
