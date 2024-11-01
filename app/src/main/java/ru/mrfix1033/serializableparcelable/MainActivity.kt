package ru.mrfix1033.serializableparcelable

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextSurname: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var buttonSave: Button
    private lateinit var listView: ListView

    private val personsList = mutableListOf<Person>()
    private lateinit var adapter: ArrayAdapter<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextSurname = findViewById(R.id.editTextSurname)
        editTextAddress = findViewById(R.id.editTextAddress)
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        buttonSave = findViewById(R.id.buttonSave)
        listView = findViewById(R.id.listView)

        adapter =
            object : ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, personsList) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup) = run {
                    val view = convertView ?: LayoutInflater.from(context)
                        .inflate(android.R.layout.simple_list_item_2, null)
                    val user = getItem(position)!!
                    val title = view.findViewById<TextView>(android.R.id.text1)
                    val subtitle = view.findViewById<TextView>(android.R.id.text2)
                    title.text = user.name
                    subtitle.text = user.phoneNumber
                    view
                }
            }.also { listView.adapter = it }

        fun EditText.pop(): String {
            val toReturn = text.toString()
            text.clear()
            return toReturn
        }

        buttonSave.setOnClickListener {
            val person = Person(
                editTextName.pop(), editTextSurname.pop(),
                editTextAddress.pop(), editTextPhoneNumber.pop()
            )
            personsList.add(person)
            adapter.notifyDataSetChanged()
        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra(Person::class.simpleName, personsList[position])
                startActivityForResult.launch(intent)
            }
    }

    private val startActivityForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            FinishState.DELETE -> {
                val index = result.data!!.getIntExtra("position", -1)
                personsList.removeAt(index)
                adapter.notifyDataSetChanged()
            }
        }
    }
}