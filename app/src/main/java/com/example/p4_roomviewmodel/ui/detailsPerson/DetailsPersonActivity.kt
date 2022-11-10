package com.example.p4_roomviewmodel.ui.detailsPerson

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.p4_roomviewmodel.data.Repository
import com.example.p4_roomviewmodel.data.database.RoomPersonDB
import com.example.p4_roomviewmodel.databinding.DetailsPersonBinding
import com.example.p4_roomviewmodel.domain.model.Person
import com.example.p4_roomviewmodel.domain.usecases.UsecaseGetPerson
import com.example.p4_roomviewmodel.domain.usecases.UsecaseUpdatePerson
import com.example.p4_roomviewmodel.domain.usecases.UsecaseValidatedPerson
import com.example.p4_roomviewmodel.ui.recycler.RecyclerActivity
import com.example.p4_roomviewmodel.ui.common.Constants
import com.example.p4_roomviewmodel.utils.StringProvider
import timber.log.Timber

class DetailsPersonActivity : AppCompatActivity() {

    private lateinit var binding: DetailsPersonBinding

    private val viewModel: DetailsPersonViewModel by viewModels {
        DetailsPersonViewModelFactory(
            StringProvider(this),
            UsecaseGetPerson(Repository(RoomPersonDB.getDatabase(this).personDao())),
            UsecaseUpdatePerson(Repository(RoomPersonDB.getDatabase(this).personDao())),
            UsecaseValidatedPerson(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        personDetails()
        changeStatus()
        setOnClickListener()
    }

    private fun changeStatus() {
        viewModel.uiState.observe(this@DetailsPersonActivity) { state ->
            val person = state.person
            val message = state.message
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                viewModel.handleEvent(
                    DetailsPersonEvent.ErrorShown
                )
                Timber.tag(Constants.TIMBER.string).w(message)
            }else{
                seePersonDetails(person)
            }
        }
    }

    private fun setOnClickListener() {
        with(binding) {

            btnUpdate.setOnClickListener {
                updatePersonDetails()
            }

            btnBack.setOnClickListener {
                goBack()
            }
        }
    }

    //llamada del ViewModel
    private fun seePersonDetails(personState: Person) {
        val person: Person = personState
        with(binding){
            etNameDetail.editText?.setText(person.name)
            etPasswordDetail.editText?.setText(person.password)
            etPhoneDetail.editText?.setText(person.phone.toString())
        }
    }

    private fun updatePersonDetails(){
        with(binding) {
            val nameNew = etNameDetail.editText?.text.toString()
            val passNew = etPasswordDetail.editText?.text.toString()
            val phoneNew = etPhoneDetail.editText?.text.toString()
            val personNew = Person(-1, nameNew, passNew, phoneNew.toInt())
            viewModel.handleEvent(
                DetailsPersonEvent.UpdatePerson(personNew)
            )
        }
    }

    private fun personDetails(){
        val idPerson: Int = extraPositionPersonRecyclerActivity()
        viewModel.handleEvent(
            DetailsPersonEvent.GetPerson(idPerson)
        )
    }

    private fun extraPositionPersonRecyclerActivity() : Int {
        var idPerson: Int = -1
        intent.extras?.let {
            idPerson = it.getInt(Constants.ID_PERSON.string)
        }
        return idPerson
    }

    private fun goBack(){
        val intent =  Intent(this@DetailsPersonActivity, RecyclerActivity::class.java)
        startActivity(intent)
    }
}