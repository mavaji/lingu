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

package parsmorph.service;

import parsmorph.model.Affix;
import parsmorph.model.Entry;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import core.util.MyTreeNode;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Vahid Mavaji
 */
public class Possibility {
    private String category;
    private Entry stem;
    private Entry root;

    private Affix[] prefixes;
    private Affix[] suffixes;

    private Affix[] auxiliariesSuffixes;
    private Affix[] auxiliariesPrefixes;

    private Affix[] derivationalSuffixes;
    private Affix[] derivationalPrefixes;

    private List<Entry[]> compounds;

    private TreeNode persianTreeNode;
    private TreeNode latinTreeNode;

    public Possibility(Entry stem) {
        this.stem = stem;
    }

    public Possibility(Entry stem, Affix[] suffixes) {
        this.stem = stem;
        this.suffixes = suffixes;
    }

    public Possibility(String category, Entry stem) {
        this.category = category;
        this.stem = stem;
    }

    public Possibility(String category, Entry stem, Entry root, Affix[] prefixes, Affix[] suffixes,
                       Affix[] auxiliariesSuffixes, Affix[] auxiliariesPrefixes, Affix[] derivationalSuffixes,
                       Affix[] derivationalPrefixes, List<Entry[]> compounds) {
        this.category = category;
        this.stem = stem;
        this.root = root;
        this.prefixes = prefixes;
        this.suffixes = suffixes;
        this.auxiliariesSuffixes = auxiliariesSuffixes;
        this.auxiliariesPrefixes = auxiliariesPrefixes;
        this.derivationalSuffixes = derivationalSuffixes;
        this.derivationalPrefixes = derivationalPrefixes;
        this.compounds = compounds;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Entry getStem() {
        return stem;
    }

    public void setStem(Entry stem) {
        this.stem = stem;
    }

    public Entry getRoot() {
        return root;
    }

    public void setRoot(Entry root) {
        this.root = root;
    }

    public Affix[] getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(Affix[] prefixes) {
        this.prefixes = prefixes;
    }

    public Affix[] getSuffixes() {
        return suffixes;
    }

    public void setSuffixes(Affix[] suffixes) {
        this.suffixes = suffixes;
    }

    public Affix[] getAuxiliariesSuffixes() {
        return auxiliariesSuffixes;
    }

    public void setAuxiliariesSuffixes(Affix[] auxiliariesSuffixes) {
        this.auxiliariesSuffixes = auxiliariesSuffixes;
    }

    public Affix[] getAuxiliariesPrefixes() {
        return auxiliariesPrefixes;
    }

    public void setAuxiliariesPrefixes(Affix[] auxiliariesPrefixes) {
        this.auxiliariesPrefixes = auxiliariesPrefixes;
    }

    public Affix[] getDerivationalSuffixes() {
        return derivationalSuffixes;
    }

    public void setDerivationalSuffixes(Affix[] derivationalSuffixes) {
        this.derivationalSuffixes = derivationalSuffixes;
    }

    public Affix[] getDerivationalPrefixes() {
        return derivationalPrefixes;
    }

    public void setDerivationalPrefixes(Affix[] derivationalPrefixes) {
        this.derivationalPrefixes = derivationalPrefixes;
    }

    public List<Entry[]> getCompounds() {
        return compounds;
    }

    public void setCompounds(List<Entry[]> compounds) {
        this.compounds = compounds;
    }

    public TreeNode getPersianTreeNode() {
        return persianTreeNode;
    }

    public void setPersianTreeNode(TreeNode persianTreeNode) {
        this.persianTreeNode = persianTreeNode;
    }

    public TreeNode getLatinTreeNode() {
        return latinTreeNode;
    }

    public void setLatinTreeNode(TreeNode latinTreeNode) {
        this.latinTreeNode = latinTreeNode;
    }

    public void buildPersianTreeNode(String morpheme) {
        persianTreeNode = new DefaultTreeNode("TreeNode", null);
        buildTreeNode(persianTreeNode, morpheme, false, true);
    }

    public void buildLatinTreeNode(String morpheme) {
        latinTreeNode = new DefaultTreeNode("TreeNode", null);
        buildTreeNode(latinTreeNode, morpheme, true, true);
    }

    private void buildTreeNode(TreeNode treeNode, String morpheme, boolean en, boolean expanded) {
        TreeNode morphemeNode = new DefaultTreeNode(new MyTreeNode("/ " + morpheme + " /", "red"), treeNode);
        morphemeNode.setExpanded(expanded);
        ResourceBundle bundle = en ? ResourceBundle.getBundle("Messages_en") : ResourceBundle.getBundle("Messages_fa");

        if (this.prefixes != null && this.prefixes.length > 0) {
            TreeNode prefixesNode = new DefaultTreeNode(new MyTreeNode(bundle.getString("prefixes"), "green"), morphemeNode);
            prefixesNode.setExpanded(expanded);
            for (Affix prefix : this.prefixes) {
                TreeNode prefixNode = new DefaultTreeNode(new MyTreeNode("/ " + (en ? prefix.getPhonologicalForm() : prefix.getWrittenForm()) + " /", "green"), prefixesNode);
                prefixNode.setExpanded(expanded);
                new DefaultTreeNode(new MyTreeNode(en ? prefix.getAffixType().getCode() : prefix.getAffixType().getName(), "green"), prefixNode).setExpanded(expanded);
            }
        }

        if (this.auxiliariesPrefixes != null && this.auxiliariesPrefixes.length > 0) {
            TreeNode auxiliariesPrefixesNode = new DefaultTreeNode(new MyTreeNode(bundle.getString("auxiliariesPrefixes"), "green"), morphemeNode);
            auxiliariesPrefixesNode.setExpanded(expanded);
            for (Affix auxiliariesPrefix : this.auxiliariesPrefixes) {
                TreeNode auxiliariesPrefixNode = new DefaultTreeNode(new MyTreeNode("/ " + (en ? auxiliariesPrefix.getPhonologicalForm() : auxiliariesPrefix.getWrittenForm()) + " /", "green"), auxiliariesPrefixesNode);
                auxiliariesPrefixNode.setExpanded(expanded);
                new DefaultTreeNode(new MyTreeNode(en ? auxiliariesPrefix.getAffixType().getCode() : auxiliariesPrefix.getAffixType().getName(), "green"), auxiliariesPrefixNode).setExpanded(expanded);
            }
        }

        TreeNode stemNode = new DefaultTreeNode(new MyTreeNode(bundle.getString("stem"), "black"), morphemeNode);
        stemNode.setExpanded(expanded);
        TreeNode stem = new DefaultTreeNode(new MyTreeNode("/ " + (en ? this.stem.getPhonologicalForm() : this.stem.getWrittenForm()) + " /", "black"), stemNode);
        stem.setExpanded(expanded);
        if (this.stem != null && this.stem.getId() != null) {
            new DefaultTreeNode(new MyTreeNode(en ? this.stem.getCategory().getCode() : this.stem.getCategory().getName(), "black"), stem).setExpanded(expanded);
        }

        if (this.derivationalPrefixes != null || this.derivationalSuffixes != null) {
            TreeNode derivationNode = new DefaultTreeNode(new MyTreeNode(bundle.getString("derivation"), "#a52a2a"), stemNode);
            derivationNode.setExpanded(expanded);

            if (this.derivationalPrefixes != null && this.derivationalPrefixes.length > 0) {
                TreeNode derivationalPrefixesNode = new DefaultTreeNode(new MyTreeNode(bundle.getString("derivationalPrefixes"), "#a52a2a"), derivationNode);
                derivationalPrefixesNode.setExpanded(expanded);
                for (Affix derivationalPrefix : this.derivationalPrefixes) {
                    new DefaultTreeNode(new MyTreeNode("/ " + (en ? derivationalPrefix.getPhonologicalForm() : derivationalPrefix.getWrittenForm()) + " /", "#a52a2a"), derivationalPrefixesNode).setExpanded(expanded);
                }
            }

            TreeNode rootNode = new DefaultTreeNode(new MyTreeNode(bundle.getString("root"), "black"), derivationNode);
            rootNode.setExpanded(expanded);
            TreeNode root = new DefaultTreeNode(new MyTreeNode("/ " + (en ? this.root.getPhonologicalForm() : this.root.getWrittenForm()) + " /", "black"), rootNode);
            root.setExpanded(expanded);
            new DefaultTreeNode(new MyTreeNode(en ? this.root.getCategory().getCode() : this.root.getCategory().getName(), "black"), root).setExpanded(expanded);

            if (this.derivationalSuffixes != null && this.derivationalSuffixes.length > 0) {
                TreeNode derivationalSuffixesNode = new DefaultTreeNode(new MyTreeNode(bundle.getString("derivationalSuffixes"), "#a52a2a"), derivationNode);
                derivationalSuffixesNode.setExpanded(expanded);
                for (int i = this.derivationalSuffixes.length - 1; i >= 0; i--) {
                    Affix derivationalSuffix = this.derivationalSuffixes[i];
                    new DefaultTreeNode(new MyTreeNode("/ " + (en ? derivationalSuffix.getPhonologicalForm() : derivationalSuffix.getWrittenForm()) + " /", "#a52a2a"), derivationalSuffixesNode).setExpanded(expanded);
                }
            }

        }

        if (this.compounds != null && this.compounds.size() > 0) {
            TreeNode compoundsNode = new DefaultTreeNode(new MyTreeNode(bundle.getString("compounding"), "green"), stemNode);
            compoundsNode.setExpanded(expanded);
            for (Entry[] entries : compounds) {
                TreeNode compoundNode = new DefaultTreeNode(new MyTreeNode(bundle.getString("compound"), "green"), compoundsNode);
                compoundNode.setExpanded(expanded);
                for (Entry entry : entries) {
                    TreeNode entryNode = new DefaultTreeNode(new MyTreeNode("/ " + (en ? entry.getPhonologicalForm() : entry.getWrittenForm()) + " /", "green"), compoundNode);
                    entryNode.setExpanded(expanded);
                    new DefaultTreeNode(new MyTreeNode(en ? entry.getCategory().getCode() : entry.getCategory().getName(), "green"), entryNode).setExpanded(expanded);
                }
            }
        }

        if (this.suffixes != null && this.suffixes.length > 0) {
            TreeNode suffixesNode = new DefaultTreeNode(new MyTreeNode(bundle.getString("suffixes"), "blue"), morphemeNode);
            suffixesNode.setExpanded(expanded);
            for (int i = this.suffixes.length - 1; i >= 0; i--) {
                Affix suffix = this.suffixes[i];
                TreeNode suffixNode = new DefaultTreeNode(new MyTreeNode("/ " + (en ? suffix.getPhonologicalForm() : suffix.getWrittenForm()) + " /", "blue"), suffixesNode);
                suffixNode.setExpanded(expanded);
                new DefaultTreeNode(new MyTreeNode(en ? suffix.getAffixType().getCode() : suffix.getAffixType().getName(), "blue"), suffixNode).setExpanded(expanded);
            }
        }

        if (this.auxiliariesSuffixes != null && this.auxiliariesSuffixes.length > 0) {
            TreeNode auxiliariesSuffixesNode = new DefaultTreeNode(new MyTreeNode(bundle.getString("auxiliariesSuffixes"), "blue"), morphemeNode);
            auxiliariesSuffixesNode.setExpanded(expanded);
            for (int i = this.auxiliariesSuffixes.length - 1; i >= 0; i--) {
                Affix auxiliariesSuffix = this.auxiliariesSuffixes[i];
                TreeNode auxiliariesSuffixNode = new DefaultTreeNode(new MyTreeNode("/ " + (en ? auxiliariesSuffix.getPhonologicalForm() : auxiliariesSuffix.getWrittenForm()) + " /", "blue"), auxiliariesSuffixesNode);
                auxiliariesSuffixNode.setExpanded(expanded);
                new DefaultTreeNode(new MyTreeNode(en ? auxiliariesSuffix.getAffixType().getCode() : auxiliariesSuffix.getAffixType().getName(), "blue"), auxiliariesSuffixNode).setExpanded(expanded);
            }
        }
    }

    public String toPhonology() {
        String result = "";
        if (this.prefixes != null && this.prefixes.length > 0) {
            for (Affix prefix : this.prefixes) {
                result += prefix.getPhonologicalForm();
            }
        }

        if (this.auxiliariesPrefixes != null && this.auxiliariesPrefixes.length > 0) {
            for (Affix auxiliariesPrefix : this.auxiliariesPrefixes) {
                result += auxiliariesPrefix.getPhonologicalForm();
            }
        }

        if (stem.getId() != null) {
            result += stem.getPhonologicalForm();
        }

        if (stem.getId() == null) {
            if (this.derivationalPrefixes != null && this.derivationalPrefixes.length > 0) {
                for (Affix derivationalPrefix : this.derivationalPrefixes) {
                    result += derivationalPrefix.getPhonologicalForm();
                }
            }
            if (this.root != null) {
                result += this.root.getPhonologicalForm();
            }
            if (this.derivationalSuffixes != null && this.derivationalSuffixes.length > 0) {
                for (int i = this.derivationalSuffixes.length - 1; i >= 0; i--) {
                    Affix derivationalSuffix = this.derivationalSuffixes[i];
                    result += derivationalSuffix.getPhonologicalForm();
                }
            }
        }

        if (this.suffixes != null && this.suffixes.length > 0) {
            for (int i = this.suffixes.length - 1; i >= 0; i--) {
                Affix suffix = this.suffixes[i];
                result += suffix.getPhonologicalForm();
            }
        }

        if (this.auxiliariesSuffixes != null && this.auxiliariesSuffixes.length > 0) {
            for (int i = this.auxiliariesSuffixes.length - 1; i >= 0; i--) {
                Affix auxiliariesSuffix = this.auxiliariesSuffixes[i];
                result += auxiliariesSuffix.getPhonologicalForm();
            }
        }

        return result;
    }

    public String toString() {
        try {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, false, this.getClass());
        } catch (Exception e) {
            return super.toString();
        }
    }
}
