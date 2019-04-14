package ContactsProject.DataModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

public class ContactData {

    // == fields ==

    private static final String CONTACTS_FILE = "contacts.xml";
    private static final String CONTACT = "contact";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String NOTES = "notes";
    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

    // == constructor ==

    public ContactData() {
    }

    public ObservableList<Contact> getContacts() {
        return this.contacts;
    }

    public void addContact(Contact item) {
        this.contacts.add(item);
    }

    public void deleteContact(Contact item) {
        this.contacts.remove(item);
    }

    public void loadContacts() {
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream("contacts.xml");
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            Contact contact = null;

            while (true) {
                while (eventReader.hasNext()) {
                    XMLEvent event = eventReader.nextEvent();
                    if (event.isStartElement()) {
                        StartElement startElement = event.asStartElement();
                        if (startElement.getName().getLocalPart().equals("contact")) {
                            contact = new Contact();
                            continue;
                        }

                        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("first_name")) {
                            event = eventReader.nextEvent();
                            contact.setFirstName(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart().equals("last_name")) {
                            event = eventReader.nextEvent();
                            contact.setLastName(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart().equals("phone_number")) {
                            event = eventReader.nextEvent();
                            contact.setPhoneNumber(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart().equals("notes")) {
                            event = eventReader.nextEvent();
                            contact.setNotes(event.asCharacters().getData());
                            continue;
                        }
                    }

                    if (event.isEndElement()) {
                        EndElement endElement = event.asEndElement();
                        if (endElement.getName().getLocalPart().equals("contact")) {
                            this.contacts.add(contact);
                        }
                    }
                }

                return;
            }
        } catch (FileNotFoundException var7) {
        } catch (XMLStreamException var8) {
            var8.printStackTrace();
        }

    }

    public void saveContacts() {
        try {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream("contacts.xml"));
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");
            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            eventWriter.add(end);
            StartElement contactsStartElement = eventFactory.createStartElement("", "", "contacts");
            eventWriter.add(contactsStartElement);
            eventWriter.add(end);
            Iterator var7 = this.contacts.iterator();

            while (var7.hasNext()) {
                Contact contact = (Contact) var7.next();
                this.saveContact(eventWriter, eventFactory, contact);
            }

            eventWriter.add(eventFactory.createEndElement("", "", "contacts"));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();
        } catch (FileNotFoundException var9) {
            System.out.println("Problem with Contacts file: " + var9.getMessage());
            var9.printStackTrace();
        } catch (XMLStreamException var10) {
            System.out.println("Problem writing contact: " + var10.getMessage());
            var10.printStackTrace();
        }

    }

    private void saveContact(XMLEventWriter eventWriter, XMLEventFactory eventFactory, Contact contact) throws FileNotFoundException, XMLStreamException {
        XMLEvent end = eventFactory.createDTD("\n");
        StartElement configStartElement = eventFactory.createStartElement("", "", "contact");
        eventWriter.add(configStartElement);
        eventWriter.add(end);
        this.createNode(eventWriter, "first_name", contact.getFirstName());
        this.createNode(eventWriter, "last_name", contact.getLastName());
        this.createNode(eventWriter, "phone_number", contact.getPhoneNumber());
        this.createNode(eventWriter, "notes", contact.getNotes());
        eventWriter.add(eventFactory.createEndElement("", "", "contact"));
        eventWriter.add(end);
    }

    private void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }
}