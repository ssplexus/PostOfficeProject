package PostOffice;

import java.util.*;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс почтового отделения
 */
public class PostOffice
{
    private List<Mailbox> mailboxes; // почтовые ящики
    private List<Letter> letters; // письма

    public PostOffice()
    {
        mailboxes = null;
        letters = null;
    }

    /**
     *  Метод генерации почтовых ящиков через итерацию
     * @param limit - лимит генерации
     * @param mailboxesGenerator - функциональный интерфейс для итератора
     */
    public void generateMailboxes(int limit, UnaryOperator<Mailbox> mailboxesGenerator)
    {
        Stream<Mailbox> streamMailboxes = Stream.iterate(new Mailbox(new Address()), mailboxesGenerator).limit(limit);
        // Адреса должны быть уникальными, собираем в HashSet
        HashSet<Mailbox> hset = (HashSet<Mailbox>) streamMailboxes.collect(Collectors.toSet());
        // Понадобится множественный доступ по номеру элемента, переводим в лист
        mailboxes = new ArrayList<>(hset);
    }

    /**
     * Метод генерации писем
     * @param limit - лимит генерации
     * @param lettersGenerator - функциональный интерфейс для генерации случайных адресов
     */
    public void generateLetters(int limit,Supplier<Letter> lettersGenerator)
    {
        Stream<Letter> streamLetters = Stream.generate(lettersGenerator).limit(limit);
        // Сборка результата генерации в список
        letters = streamLetters.collect(Collectors.toList());
    }

    /**
     * Метод отправки писем адресатам
     */
    public void send()
    {
        // Запуск потока почтовых ящиков
        mailboxes.stream().
                peek(mailbox -> mailbox.put(letters.stream().
                        // отбор писем соответствующих адресу ящика
                        filter(letter -> letter.getAddress().equals(mailbox.getAddress())).
                        collect(Collectors.toList())) // кладём отобранные письма в ящик
                //сортируем результат в порядке убывания количества писем в ящике
                ).sorted(Comparator.comparingInt((Mailbox o) -> -o.getLettersCount())).
                forEach(System.out::println); // вывод результата
    }

    /**
     * Геттер почтовых ящиков
     * @return
     */
    public List<Mailbox> getMailboxes()
    {
        return mailboxes;
    }

    /**
     * Статический метод генерации
     * @param maxRandIdx - максимальный индекс генерации
     * @param mailboxes - почтовые ящики
     * @return - случайный адрес
     */
    public static Address getRandomAddress(int maxRandIdx, List<Mailbox> mailboxes)
    {
        // Если нет списка почтовых ящиков, то применяется статический метод класса Address,
        // для генерации случайного адреса
        if(mailboxes == null) return new Address(Address.getRandom());

        int randIdx = new Random().nextInt(maxRandIdx); // получаем случайный индекс
        if(randIdx >= mailboxes.size() ) return new Address(); // если за границей списка, то адрес по умолчанию

        return new Address(mailboxes.get(randIdx).getAddress()); // случайный адресс из существующих
    }

}
