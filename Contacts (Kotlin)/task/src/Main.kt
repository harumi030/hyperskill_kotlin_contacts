package contacts

import com.squareup.moshi.*
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.datetime.*
import java.io.File

var app = PhoneBook()

var file: File? = null

val adapterFactory = PolymorphicJsonAdapterFactory
    .of(Contact::class.java, "contact")
    .withSubtype(PersonContact::class.java, "person")
    .withSubtype(OrganizationContact::class.java, "organization")

val moshi = Moshi.Builder()
    .add(adapterFactory)
    .add(KotlinJsonAdapterFactory())
    .build()

val type = Types.newParameterizedType(MutableList::class.java, Contact::class.java)
val adapter = moshi.adapter<List<Contact>>(type)

fun main(args: Array<String>) {
    val location = if(args.isNotEmpty()) args[0].toString() else "/Users/terayhar/IdeaProjects/Contacts (Kotlin)/Contacts (Kotlin)/task/src/phonebook.txt"
    file = if(location.isNotEmpty()) File(location) else null

    println("open phonebook.db")

    var action = ""

    while (action != "exit") {
        println("[menu] Enter action (add, list, search, count, exit):")

        val option = readln()
        when (option) {
            "add" -> runAddSequence()
            "count" -> runCountSequence()
            "search" -> runSearchSequence()
            "list" -> listContacts()
        }

        action = option
    }
}

open class Contact() {
    private var number: String = ""
        get() = if (field.isNotEmpty()) field else "[no number]"
        set(value) {
            val numberRegex = """^\+?\d?[\s-]?(\(\w{2,}\)|\w{2,})[-\s]?(\(\w{2,}\)|\w{2,})?[-\s]?(\(\w{2,}\)|\w{2,})""".toRegex()
            if (numberRegex.matches(value)) {
                field = value
            } else {
                field = ""
                println("Wrong number format!")
            }
        }

    open val name: String = ""

    fun updateNumber(value: String) {
        this.number = value
        lastEdited = Clock.System.now().toLocalDateTime(TimeZone.UTC).toString()
    }

    val created = Clock.System.now().toLocalDateTime(TimeZone.UTC).toString()

    var lastEdited = Clock.System.now().toLocalDateTime(TimeZone.UTC).toString()

    open fun getAllProperties(): String {
        return listOf(name, getProperty("number")).joinToString()
    }

    open fun getEditableProperties(): List<String> {
        println("override to implement a specific edit function")
        return listOf("number")
    }

    open fun setProperty(property: String, value: String) {
        println("override to implement a specific set function")
    }

    open fun getProperty(property: String): String {
        println("override to implement a specific get function")
        return ""
    }
}

class PersonContact(
    var number: String,
    var firstName: String,
    var surname: String,
    var birthdate: String,
    var gender: String
) : Contact() {

    init {
        super.updateNumber(this.number)
    }

    override val name: String
        get() = "$firstName $surname"

    fun updateGender(value: String) {
        if (isValidGender(value)) {
            gender = value
        } else {
            println("Bad gender!")
            gender = "[no data]"
        }
    }

    fun updateBirthdate(value: String) {
        if (isValidDate(value)) {
            birthdate = value
        } else {
            println("Bad birthdate!")
            birthdate = "[no data]"
        }
    }

    override fun getAllProperties(): String {
        return listOf(firstName, surname, birthdate, gender, number).joinToString()
    }

    override fun getEditableProperties(): List<String> {
        return listOf("name", "surname", "birthdate", "gender", "number")
    }

    override fun getProperty(property: String): String {
        return when (property) {
            "name" -> firstName
            "surname" -> surname
            "birthdate" -> birthdate
            "gender" -> gender
            "number" -> number
            else -> "No matching property"
        }
    }

    override fun setProperty(property: String, value: String) {
        when (property) {
            "name" -> firstName = value
            "surname" -> surname = value
            "birthdate" -> updateBirthdate(value)
            "gender" -> updateGender(value)
            "number" -> super.updateNumber(value)
            else -> println("Unknown property")
        }
    }
}

class OrganizationContact(override var name: String, var address: String, var number: String) : Contact() {

    init {
        super.updateNumber(number)
    }

    override fun getAllProperties(): String {
        return listOf(name, address, number).joinToString()
    }

    override fun getEditableProperties(): List<String> {
        return listOf("name", "address", "number")
    }

    override fun getProperty(property: String): String {
        return when (property) {
            "name" -> name
            "address" -> address
            "number" -> number
            else -> "Unknown property"
        }
    }

    override fun setProperty(property: String, value: String) {
        when (property) {
            "name" -> name = value
            "address" -> address = value
            "number" -> updateNumber(value)
            else -> println("Unknown property")
        }
    }
}

class PhoneBook(val contacts: MutableList<Contact> = mutableListOf<Contact>()) {
    fun getSize() = contacts.size

    fun addContact(contact: Contact) = contacts.add(contact)

    fun removeContact(index: Int) = contacts.removeAt(index)
}

fun runAddSequence() {
    println("Enter the type (person, organization):")
    val type = readln()

    if (type == "person") addPerson()

    if (type == "organization") addOrganization()

    println("The record added.")
    println("")
}

fun addPerson() {
    println("Enter the name:")
    val name = readln()

    println("Enter the surname:")
    val surname = readln()

    println("Enter the birth date:")
    var birthdate = readln()
    // validate the birthday input
    if (!isValidDate(birthdate)) {
        println("Bad birth date!")
        birthdate = "[no data]"
    }

    println("Enter the gender (M, F):")
    var gender = readln()
    // validate the gender input
    if (!isValidGender(gender)) {
        println("Bad gender!")
        gender = "[no data]"
    }

    println("Enter the number:")
    val number = readln()

    val newContact = PersonContact(number, name, surname, birthdate, gender)
    app.addContact(newContact)
    saveToFile()
    println("")
}

fun addOrganization() {
    println("Enter the organization name:")
    val name = readln()

    println("Enter the address:")
    val address = readln()

    println("Enter the number:")
    val number = readln()

    val newContact = OrganizationContact(name, address, number)
    app.addContact(newContact)
}

fun runRemoveSequence(record: Int) {
    app.removeContact(record - 1)
    println("the record removed!")
    println("")
    saveToFile()
}

fun runEditSequence(record: Int) {
    val contact = app.contacts[record - 1]
    val properties = contact.getEditableProperties()

    println("Select a field (${properties.joinToString(", ")}):")
    val field = readln()

    println("Enter $field:")
    val value = readln()

    contact.setProperty(field, value)

    println("The record updated!")
    saveToFile()

    modifyRecordSequence(record)
    println("")
}

fun runCountSequence() {
    println("The Phone Book has ${app.getSize()} records.")
    println("")
}

fun listContacts() {
    for (index in app.contacts.indices) {
        println("${index + 1}. ${app.contacts[index].name}, ${app.contacts[index].getProperty("number")}")
    }
    println("")

    println("[list] Enter action ([number], back):")
    val selection = readln()

    if (selection.toIntOrNull() != null) {
        runInfoSequence(selection.toInt())
    }

    if (selection == "back") return
}

fun runInfoSequence(index: Int) {

    if (app.contacts[index - 1] is PersonContact) {
        val person = app.contacts[index - 1] as PersonContact

        println("Name: ${person.firstName}")
        println("Surname: ${person.surname}")
        println("Birth date: ${person.birthdate}")
        println("Gender: ${person.gender}")
        println("Number: ${person.getProperty("number")}")
        println("Time created: ${person.created}")
        println("Time last edit: ${person.lastEdited}")
    } else {
        val organization = app.contacts[index - 1] as OrganizationContact

        println("Organization name: ${organization.name}")
        println("Address: ${organization.address}")
        println("Number: ${organization.getProperty("number")}")
        println("Time created: ${organization.created}")
        println("Time last edit: ${organization.lastEdited}")
    }
    println("")

    modifyRecordSequence(index)
}

fun modifyRecordSequence (index: Int) {
    println("[record] Enter action (edit, delete, menu):")
    val selection = readln()

    when (selection) {
        "edit" -> runEditSequence(index)
        "delete" -> runRemoveSequence(index)
        "menu" -> return
    }
}

fun runSearchSequence() {
    println("Enter search query:")
    val searchStr = readln()
    val matches = app.contacts.filter { contact ->
        val dataStr = contact.getAllProperties()

        dataStr.contains(searchStr, ignoreCase = true) }

    println("Found ${matches.size} result:")
    for (index in matches.indices) {
        println("${index + 1}. ${matches[index].name}")
    }

    println("[search] Enter action ([number], back, again):")
    val selection = readln()

    if (selection.toIntOrNull() != null) {
        runInfoSequence(selection.toInt())
        return
    } else {
        when (selection) {
            "again" -> runSearchSequence()
            "back" -> return
        }
    }
}

/**
 * Util functions
 */
fun isValidGender(gender: String) = gender.matches("[MF]".toRegex())

fun isValidDate(date: String) = date.matches("\\d{4}-\\d{2}-\\d{2}".toRegex())

fun saveToFile() {
    val json = adapter.toJson(app.contacts)
    file!!.writeText(json)
}