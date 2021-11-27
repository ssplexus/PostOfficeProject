package PostOffice;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

/**
 *  Класс письма
 */
public class Letter
{
    private static int lettersCnt; // счётчик писем
    private int id; // идентификатор письма
    private LocalDate date; // дата отправки письма
    private Address address; // адрес адресата

    /**
     * Конструктор письма
     * @param address // адрес адресата
     */
    public Letter(Address address)
    {
        id = lettersCnt++;
        this.address = address;
        // Случайное значение даты отправки в пределах двух лет от настоящего времени
        date = LocalDate.now().minusDays(new Random().nextInt(365*2));
    }

    @Override
    public String toString()
    {

        return "letter-" + id + "[ date=" + date + ", address=" +
                (Optional.ofNullable(address).isPresent() ? address.toString():"empty") +
                "]";
    }

    /**
     * Геттер адреса адресата
     * @return
     */
    public Address getAddress()
    {
        return address;
    }
}
