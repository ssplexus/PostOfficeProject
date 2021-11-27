import PostOffice.PostOffice;
import PostOffice.Letter;
import PostOffice.Address;
import PostOffice.Mailbox;

import java.util.Optional;

public class Main
{
    public static void main(String[] args)
    {
        // Создание постового отделения
        PostOffice postOffice = new PostOffice();

        // Генерация почтовых ящиков
        postOffice.generateMailboxes(150, (mailbox)->mailbox.getAddress().hasNext()? // если есть вариант адреса
                new Mailbox(mailbox.getAddress().getNextAddr()): // создать почтовый ящик с этим адресом
                new Mailbox(new Address())); // если нет, то создаётся почтовый ящик с адресом по умолчанию

        // Генерация писем
        postOffice.generateLetters(10000, ()->new Letter(
                PostOffice.getRandomAddress(150, postOffice.getMailboxes()) // случайная генерация писем
        ));
        postOffice.send();
    }
}
