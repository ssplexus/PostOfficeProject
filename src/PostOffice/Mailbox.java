package PostOffice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Класс почтового ящика
 */
public class Mailbox
{
    private static int mailBoxesCnt; // счётчик ящиков
    private int id; // идентификатор ящика

    private Address address; // адрес ящика
    private List<Letter> letters; // письма в ящике

    /**
     * Конструктор ящика
     * @param address - адрес ящика
     */
    public Mailbox(Address address)
    {
        id = mailBoxesCnt++;
        this.address = address;
    }

    /**
     * Геттер адреса ящика
     * @return
     */
    public Address getAddress()
    {
        return address;
    }

    /**
     * Метод, чтобы добавить письма в ящик
     * @param letters - письма
     */
    public void put(List<Letter> letters)
    {
        if(this.letters == null) this.letters = new ArrayList<>();
        this.letters.addAll(letters);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mailbox mailbox = (Mailbox) o;
        return id == mailbox.id && address.equals(mailbox.address);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, address);
    }

    @Override
    public String toString() {
        return "Mailbox-" + id + ": [address=" +
                (Optional.ofNullable(address).isPresent() ? Optional.of(address).get(): "") +
                "] " + "[total_letters=" +
                (Optional.ofNullable(letters).isPresent() ? letters.size(): 0) + "]";
    }

    /**
     * Получить количество писем в ящике
     * @return
     */
    public int getLettersCount()
    {
        return letters.size();
    }
}
