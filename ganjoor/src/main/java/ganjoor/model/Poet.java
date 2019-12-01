package ganjoor.model;


import core.model.BaseEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vahid
 * Date: 4/18/11
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "GJ_POET")
public class Poet extends BaseEntity {
    private String name;

    private String description;

    private Category category;

    private Long century;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne
    @JoinColumn(name = "cat_id")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(name = "century")
    public Long getCentury() {
        return century;
    }

    public void setCentury(Long century) {
        this.century = century;
    }

    private static Map<Long, String> poetCodes = null;

    public static void initializePoetCode() {
        poetCodes = new HashMap<Long, String>();

        poetCodes.put(HAFIZ, "HAFIZ");
        poetCodes.put(KHAYYAM, "KHAYYAM");
        poetCodes.put(FERDOWSI, "FERDOWSI");
        poetCodes.put(MOLAVI, "MOLAVI");
        poetCodes.put(NEZAMI, "NEZAMI");
        poetCodes.put(SAADI, "SAADI");
        poetCodes.put(ETESAMI, "ETESAMI");
        poetCodes.put(ATTAR, "ATTAR");
        poetCodes.put(SANAIEE, "SANAIEE");
        poetCodes.put(VAHSHI, "VAHSHI");
        poetCodes.put(RUDAKI, "RUDAKI");
        poetCodes.put(NASSER_KOSROW, "NASSER_KOSROW");
        poetCodes.put(MANUCHEHRI, "MANUCHEHRI");
        poetCodes.put(FARROKHI, "FARROKHI");
        poetCodes.put(KHAGHANI, "KHAGHANI");
        poetCodes.put(SAAD_SALMAN, "SAAD_SALMAN");
        poetCodes.put(ANVARI, "ANVARI");
        poetCodes.put(AWHADI, "AWHADI");
        poetCodes.put(KHAJOO_KERMANI, "KHAJOO_KERMANI");
        poetCodes.put(ARAGHI, "ARAGHI");
        poetCodes.put(SAAEB, "SAAEB");
        poetCodes.put(SHABESTARI, "SHABESTARI");
        poetCodes.put(JAAMI, "JAAMI");
        poetCodes.put(HATEF, "HATEF");
        poetCodes.put(ABU_SAEED, "ABU_SAEED");
        poetCodes.put(BAHAR, "BAHAR");
        poetCodes.put(BABA_TAHER, "BABA_TAHER");
        poetCodes.put(MOHTASHAM, "MOHTASHAM");
        poetCodes.put(SHEIKH_BAHAIEE, "SHEIKH_BAHAIEE");
        poetCodes.put(SEIF_FORGHANI, "SEIF_FORGHANI");
        poetCodes.put(FOROUGHI_BASTAMI, "FOROUGHI_BASTAMI");
        poetCodes.put(OBEID_ZAKANI, "OBEID_ZAKANI");
        poetCodes.put(DEHLAVI, "DEHLAVI");
        poetCodes.put(SHAHRYAR, "SHAHRYAR");
        poetCodes.put(JEBELLI, "JEBELLI");
        poetCodes.put(GORGANI, "GORGANI");
        poetCodes.put(FEIZ_KASHANI, "FEIZ_KASHANI");
        poetCodes.put(SALMAN_SAVEJI, "SALMAN_SAVEJI");
        poetCodes.put(RAHI_MOAYERI, "RAHI_MOAYERI");
        poetCodes.put(EGHBAL, "EGHBAL");
        poetCodes.put(BIDEL, "BIDEL");
        poetCodes.put(GHAANI, "GHAANI");
        poetCodes.put(KASAIEE, "KASAIEE");
        poetCodes.put(ORFI, "ORFI");
        poetCodes.put(ARTIMANI, "ARTIMANI");
        poetCodes.put(NIMA, "NIMA");
        poetCodes.put(SHAMLOO, "SHAMLOO");
        poetCodes.put(SOHRAB, "SOHRAB");
        poetCodes.put(FOROUGH, "FOROUGH");
        poetCodes.put(SIMIN_BEHBEHANI, "SIMIN_BEHBEHANI");
        poetCodes.put(KHOMEINI, "KHOMEINI");
        poetCodes.put(SABZEVARI, "SABZEVARI");
    }

    @Transient
    public static Map<Long, String> getPoetCodes() {
        if (poetCodes == null) {
            initializePoetCode();
        }
        return poetCodes;
    }

    @Transient
    public static Long[] getAllPoets() {
        if (poetCodes == null) {
            initializePoetCode();
        }
        return poetCodes.keySet().toArray(new Long[1]);

    }

    public static final Long HAFIZ = 2L;
    public static final Long KHAYYAM = 3L;
    public static final Long FERDOWSI = 4L;
    public static final Long MOLAVI = 5L;
    public static final Long NEZAMI = 6L;
    public static final Long SAADI = 7L;
    public static final Long ETESAMI = 8L;
    public static final Long ATTAR = 9L;
    public static final Long SANAIEE = 10L;
    public static final Long VAHSHI = 11L;
    public static final Long RUDAKI = 12L;
    public static final Long NASSER_KOSROW = 13L;
    public static final Long MANUCHEHRI = 14L;
    public static final Long FARROKHI = 15L;
    public static final Long KHAGHANI = 16L;
    public static final Long SAAD_SALMAN = 17L;
    public static final Long ANVARI = 18L;
    public static final Long AWHADI = 19L;
    public static final Long KHAJOO_KERMANI = 20L;
    public static final Long ARAGHI = 21L;
    public static final Long SAAEB = 22L;
    public static final Long SHABESTARI = 23L;
    public static final Long JAAMI = 24L;
    public static final Long HATEF = 25L;
    public static final Long ABU_SAEED = 26L;
    public static final Long BAHAR = 27L;
    public static final Long BABA_TAHER = 28L;
    public static final Long MOHTASHAM = 29L;
    public static final Long SHEIKH_BAHAIEE = 30L;
    public static final Long SEIF_FORGHANI = 31L;
    public static final Long FOROUGHI_BASTAMI = 32L;
    public static final Long OBEID_ZAKANI = 33L;
    public static final Long DEHLAVI = 34L;
    public static final Long SHAHRYAR = 35L;
    public static final Long JEBELLI = 37L;
    public static final Long GORGANI = 38L;
    public static final Long FEIZ_KASHANI = 39L;
    public static final Long SALMAN_SAVEJI = 40L;
    public static final Long RAHI_MOAYERI = 41L;
    public static final Long EGHBAL = 42L;
    public static final Long BIDEL = 43L;
    public static final Long GHAANI = 44L;
    public static final Long KASAIEE = 45L;
    public static final Long ORFI = 46L;
    public static final Long ARTIMANI = 47L;
    public static final Long NIMA = 501L;
    public static final Long SHAMLOO = 502L;
    public static final Long SOHRAB = 503L;
    public static final Long FOROUGH = 504L;
    public static final Long SIMIN_BEHBEHANI = 505L;
    public static final Long KHOMEINI = 600L;
    public static final Long SABZEVARI = 602L;
}
