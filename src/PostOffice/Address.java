package PostOffice;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс адреса
 */
public class Address
{
    private static final int STR_LIMIT = 3; // Константа длины строк названий городов и улиц
    private String town;
    private String street;
    private int home;
    private Address nextAddr; // переменная для хранения следующего адреса

    /**
     * Конструктор по умолчанию
     */
    public Address()
    {
        this("Aaa", "Aaa", 1);
    }

    public Address(String town, String street, int home)
    {
        this.town = town;
        this.street = street;
        this.home = home;
        nextAddr = null;
    }

    public Address(Address address)
    {
        this(address.getTown(), address.getStreet(), address.getHome());
    }

    /**
     * Метод определения равенства адресов
     * @param address
     * @return
     */
    public boolean equals(Address address)
    {
        return town.equals(address.getTown()) && street.equals(address.getStreet()) && home == address.getHome()? true : false;
    }

    public String getTown() {
        return town;
    }

    public String getStreet() {
        return street;
    }

    public int getHome() {
        return home;
    }

    /**
     * Метод есть ли следующая итерация адреса
     * @return да/нет
     */
    public boolean hasNext()
    {
        int home = 0;
        StringBuilder town = new StringBuilder();
        StringBuilder street = new StringBuilder();

        Pattern pattern = Pattern.compile("[a-zA-Z]{" + STR_LIMIT + "}");
        Matcher matcher;

        if(this.home >= 10) // если номер дома равен верхней границе
        {
            home = 1; // устанавливаем номер дома в начальное значение
            if(this.street.length() == STR_LIMIT && this.town.length() == STR_LIMIT)
            {
                for (int i = 0; i < STR_LIMIT; i++)
                    street.append((char)(this.street.charAt(i) + 3)); // собираем название следующей улицы

                matcher = pattern.matcher(street.toString());

                if (!matcher.find()) // если название улицы не соответствует паттерну, то меняем название города
                {
                    street = new StringBuilder();
                    street.append("Aaa"); // устанавливаем название улицы в начальное значение
                    for (int i = 0; i < STR_LIMIT; i++)
                        town.append((char)(this.town.charAt(i) + 2)); // собираем название следующего города

                    matcher = pattern.matcher(town.toString());
                    if (!matcher.find()) return false; // если название города не соответствует паттерну, то выход
                }
                else town.append(this.town);
            }
            else return false;
        }
        else
        {
            home = this.home + 1;
            town.append(this.town);
            street.append(this.street);
        }

        nextAddr = new Address(town.toString(), street.toString(),home); // собираем следующий адрес
        return true;
    }

    /**
     * Геттер следующего адреса
     * @return
     */
    public Address getNextAddr()
    {
        return nextAddr;
    }

    /**
     * Статический метод получения случайного адреса
     * @return
     */
    public static Address getRandom()
    {
        int home = 0;
        StringBuilder town = new StringBuilder();
        StringBuilder street = new StringBuilder();
        Random random = new Random();

        char randChar = (char) (97 + random.nextInt(26)); // получение случайной буквы для названия города
        town.append(String.valueOf(randChar).toUpperCase()).append(randChar).append(randChar);
        randChar = (char) (97 + random.nextInt(26)); // случайная буква для названия улицы
        street.append(String.valueOf(randChar).toUpperCase()).append(randChar).append(randChar);
        home = 1 + random.nextInt(21); // случайный номер дома

        return new Address(town.toString(), street.toString(), home); // сборка случайного адреса и возврат значения
    }

    @Override
    public String toString()
    {
        return "[town = " + town + ", street = " + street + ", home = " + home + "]";
    }
}
