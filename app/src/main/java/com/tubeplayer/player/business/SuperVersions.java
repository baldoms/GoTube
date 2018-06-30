package com.tubeplayer.player.business;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.URLDecoder;
import java.util.Locale;

import com.tubeplayer.player.app.TubeApp;

/**
 * Created by liyanju on 2018/1/1.
 */

public class SuperVersions {

    private static final String SPECIALKEY = "feater_super_app";

    public static void setSpecial() {
        SuperVersionHandler.setSpecial();
    }

    public static void initSpecial() {
        SuperVersionHandler.initSpecial();
    }

    public static boolean isSpecial() {
        return SuperVersionHandler.isSpecial();
    }


    public static class SuperVersionHandler {

        private static volatile boolean isSpecial = false;

        public static void setSpecial() {
            isSpecial = true;
            TubeApp.getPreferenceManager().edit().putBoolean(SPECIALKEY, true).apply();
        }

        public static String getPhoneCountry(Context context) {
            String country = "";
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager.getPhoneType()
                        != TelephonyManager.PHONE_TYPE_CDMA) {
                    country = telephonyManager.getNetworkCountryIso();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return country;
        }

        public static String getCountry2(Context context) {
            String country = "";
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String simCountry = telephonyManager.getSimCountryIso();
                if (simCountry != null && simCountry.length() == 2) {
                    country = simCountry.toUpperCase(Locale.ENGLISH);
                } else if (telephonyManager.getPhoneType()
                        != TelephonyManager.PHONE_TYPE_CDMA) {
                    country = telephonyManager.getNetworkCountryIso();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return country;
        }

        public static String getSimCountry(Context context) {
            String country = "";
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String simCountry = telephonyManager.getSimCountryIso();
                if (simCountry != null && simCountry.length() == 2) {
                    country = simCountry.toUpperCase(Locale.ENGLISH);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return country;
        }

        public static String getCountry(Context context) {
            String country = "";
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String simCountry = telephonyManager.getSimCountryIso();
                if (simCountry != null && simCountry.length() == 2) {
                    country = simCountry.toUpperCase(Locale.ENGLISH);
                    if (TextUtils.isEmpty(country)) {
                        country = Locale.getDefault().getCountry();
                    }
                } else if (telephonyManager.getPhoneType()
                        != TelephonyManager.PHONE_TYPE_CDMA) {
                    country = telephonyManager.getNetworkCountryIso();
                    if (TextUtils.isEmpty(country)) {
                        country = Locale.getDefault().getCountry();
                    }
                } else {
                    country = Locale.getDefault().getCountry();
                    if (!TextUtils.isEmpty(country)) {
                        country = country.toUpperCase(Locale.ENGLISH);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return country;
        }

        public static void initSpecial() {
            isSpecial = TubeApp.getPreferenceManager().getBoolean(SPECIALKEY, false);
        }

        public static boolean isSpecial() {
            return isSpecial;
        }

        public static boolean isReferrerOpen2(String urlCampaignid, String campaignid) {
            if (TextUtils.isEmpty(urlCampaignid)) {
                return false;
            }
            if (TextUtils.isEmpty(campaignid)) {
                return false;
            }

            if (campaignid.equals(urlCampaignid)) {
                return true;
            }

            return false;
        }

        public static boolean isReferrerOpen3(String referrer) {
            if (referrer.startsWith("campaigntype=")
                    && referrer.contains("campaignid=")) {
                return true;
            } else {
                return false;
            }
        }

        public static boolean isFacebookOpen(String referrer) {
            try {
                String decodeReferrer = URLDecoder.decode(referrer, "utf-8");
                String utmSource = getUtmSource(decodeReferrer);
                if (!TextUtils.isEmpty(utmSource) && utmSource.contains("not set")) {
                    return true;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return false;
        }

        public static String getUtmSource(String str) {
            if (!TextUtils.isEmpty(str)) {
                String[] split = str.split("&");
                if (split != null && split.length >= 0) {
                    for (String str2 : split) {
                        if (str2 != null && str2.contains("utm_source")) {
                            String[] split2 = str2.split("=");
                            if (split2 != null && split2.length > 1) {
                                return split2[1];
                            }
                        }
                    }
                }
            }
            return null;
        }


        public static boolean countryIfShow(Context context) {
            String country4 = getPhoneCountry(context);
            String country = getCountry2(context);
            String country3 = getSimCountry(context);

            if (TextUtils.isEmpty(country)) {
                return false;
            }

            if (!TextUtils.isEmpty(country4)
                    && !TextUtils.isEmpty(country3)
                    && !country4.toLowerCase().equals(country3.toLowerCase())
                    && Utils.isRoot()) {
                return false;
            }

            if ("br".equals(country.toLowerCase())) {
                FacebookReport.logSentReferrer2("br_country");
                return true;
            }

            if ("sa".equals(country.toLowerCase())) {
                FacebookReport.logSentReferrer2("sa_country");
                return true;
            }

            if ("id".equals(country.toLowerCase())) {
                FacebookReport.logSentReferrer2("id_country");
                return true;
            }

            if ("th".equals(country.toLowerCase())) {
                FacebookReport.logSentReferrer2("th_country");
                return true;
            }
            if ("vn".equals(country.toLowerCase())) {
                FacebookReport.logSentReferrer2("vn_country");
                return true;
            }

            return false;
        }


    }
}
