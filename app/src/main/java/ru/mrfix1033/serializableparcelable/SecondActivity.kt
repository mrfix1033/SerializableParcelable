package ru.mrfix1033.serializableparcelable

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val undefined = getString(R.string.undefined)

        position = intent.getIntExtra("position", -1)
        val person = intent.getParcelableExtra<Person>(Person::class.simpleName)!!
        findViewById<TextView>(R.id.textViewName).text =
            getString(R.string.person_name).format(person.name.ifEmpty { undefined })
        findViewById<TextView>(R.id.textViewSurname).text =
            getString(R.string.person_surname).format(person.surname.ifEmpty { undefined })
        findViewById<TextView>(R.id.textViewAddress).text =
            getString(R.string.person_address).format(person.address.ifEmpty { undefined })
        findViewById<TextView>(R.id.textViewPhoneNumber).text =
            getString(R.string.person_phone_number).format(person.phoneNumber.ifEmpty { undefined })

        findViewById<Button>(R.id.buttonBack).setOnClickListener {
            finishWithState(FinishState.NONE)
        }
        findViewById<Button>(R.id.buttonRemove).setOnClickListener {
            finishWithState(FinishState.DELETE)
        }
    }

    private fun finishWithState(state: Int) {
        val intent = Intent()
        intent.putExtra("position", position)
        setResult(state, intent)
        finish()
    }
}