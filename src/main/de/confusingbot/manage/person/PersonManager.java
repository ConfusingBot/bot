package main.de.confusingbot.manage.person;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class PersonManager
{
    public static ConcurrentHashMap<String, Person> persons = new ConcurrentHashMap<>();

    public static void instantiatePersons()
    {
        //Add Persons
        persons.put("merkel", new Person("Angela Merkel", "https://img.welt.de/img/debatte/kommentare/mobile171043860/4422501637-ci102l-w1024/92356602.jpg"));
        persons.put("trump", new Person("Donald Trump", "https://www.brandseye.com/images/news/donald-trump.jpg"));
        persons.put("patrick", new Person("Patrick", "https://www.liberaldictionary.com/wp-content/uploads/2018/12/patrick.png"));
        persons.put("chucknorris", new Person("Chuck Norris", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS3IejID4_1T0NcV0dls5j7QNI_ugkgWNpRb8-O5nuGDai0I3Fr&usqp=CAU"));
        persons.put("magret", new Person("Magret O'Kelly", "https://cdn.discordapp.com/attachments/710203506375786559/710203528697610290/unknown.png"));
        persons.put("günter", new Person("Günter Smith", "https://muehlacker-tagblatt.de/wp-content/uploads/2018/10/910_0960_82208_-568x778.jpg"));
        persons.put("grandma", new Person("Grandma", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQJgucNqkzAgtvzVtOgRu8BgeDZwsv7A_svmlDg7WnphyVkYsMj&usqp=CAU"));
        persons.put("yoda", new Person("Yoda", "https://vignette.wikia.nocookie.net/starwars/images/d/d6/Yoda_SWSB.png/revision/latest/top-crop/width/360/height/360?cb=20150206140125"));
        persons.put("smith", new Person("Captain Smith", "https://www.biography.com/.image/t_share/MTE1ODA0OTcxNDY1ODY4ODEz/edward-j.jpg"));
        persons.put("hyper", new Person("Hide the Pain Harold", "https://i.imgur.com/9GbwJ6ll.jpg"));
        persons.put("kim", new Person("Kim Jong Un", "https://img.welt.de/img/newsticker/dpa_nt/infoline_nt/brennpunkte_nt/mobile207391831/1912504207-ci102l-w1024/urn-newsml-dpa-com-20090101-200421-99-771155-large-4-3-jpg.jpg"));
    }


    public static Person getRandomPerson()
    {
        Random generator = new Random();
        Object[] values = persons.values().toArray();
        Person randomValue = (Person) values[generator.nextInt(values.length)];

        return randomValue;
    }


    public static Person getPerson(String key)
    {
        return persons.get(key);
    }


}
