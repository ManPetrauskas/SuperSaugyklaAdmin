package net.simplifiedlearning.myheroapp;

/**
 * Created by Belal on 9/9/2017.
 */

public class Api {

    private static final String ROOT_URL = "http://earwiggy-injury.000webhostapp.com/HeroApiHero/v1/Api.php?apicall=";

    public static final String URL_CREATE_HERO = ROOT_URL + "createhero";       //php veikia
    public static final String URL_READ_HEROES = ROOT_URL + "getheroes";        //php veikia
    public static final String URL_UPDATE_HERO = ROOT_URL + "updatehero";       //php veikia
    public static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";   //php veikia
    public static final String URL_GETTABLE_CHANGES_MADE = ROOT_URL + "getherochanges_made";   //php veikia

}
