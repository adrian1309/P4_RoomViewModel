package com.example.p4_roomviewmodel.ui.recycler

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.p4_roomviewmodel.R
import com.example.p4_roomviewmodel.data.Repository
import com.example.p4_roomviewmodel.data.database.RoomPersonDB
import com.example.p4_roomviewmodel.databinding.InsertDataPersonBinding
import com.example.p4_roomviewmodel.databinding.RecyclerMainBinding
import com.example.p4_roomviewmodel.domain.model.Person
import com.example.p4_roomviewmodel.domain.usecases.*
import com.example.p4_roomviewmodel.ui.common.Constants
import com.example.p4_roomviewmodel.ui.detailsPerson.DetailsPersonActivity
import com.example.p4_roomviewmodel.utils.StringProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber


class RecyclerActivity : AppCompatActivity() {

    private lateinit var binding: RecyclerMainBinding
    private var countId: Int = 0

    private val viewModel: RecyclerViewModel by viewModels {
        RecyclerViewModelFactory(
            StringProvider(this),
            UsecaseGetAllPersons(Repository(RoomPersonDB.getDatabase(this).personDao())),
            UsecaseDeletePerson(Repository(RoomPersonDB.getDatabase(this).personDao())),
            UsecaseAddPerson(Repository(RoomPersonDB.getDatabase(this).personDao())),
            UsecaseValidatedPerson(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecyclerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //cargar imagen
        chargeImage()
        //cambio de estado ViewModel
        changesStatus()
        //botones
        setOnClickListener()
    }

    /** la funcion onStart hace que cada vez que se carga este activity, ocurra lo que tiene dentro (igual que onCreate).
     * La diferencia es que el onStart tambien sirve si el usuario le da al boton de atras del movil
     */
    override fun onStart() {
        super.onStart()
        //mostrar personas iniciales
        showList()
    }

    private fun configAdapter() : PersonAdapter {
        val personAdapter = PersonAdapter(object : PersonAdapter.PersonActions {
            override fun onDeletePersonAdapter(person: Person) {
                onDeletePerson(person)
            }
            override fun onShowPersonDetailsAdapter(id: Int) {
                onShowPersonDetails(id)
            }
        })
        return personAdapter
    }

    private fun changesStatus() {
        viewModel.uiState.observe(this@RecyclerActivity) { state ->

            if (state.message != null){
                Toast.makeText(this@RecyclerActivity, state.message, Toast.LENGTH_SHORT).show()
                viewModel.handleEvent(
                    RecyclerEvent.ErrorShown
                )
                Timber.tag(Constants.TIMBER.string).i(state.message)
            } else {
                with(binding) {

                    val listPerson: List<Person> = state.listPerson

                    listPerson.let {
                        rvPersons.addItemDecoration(
                            DividerItemDecoration(
                                rvPersons.context,
                                LinearLayoutManager.HORIZONTAL
                            )
                        )
                        val adapter = configAdapter()
                        rvPersons.adapter = adapter
                        rvPersons.layoutManager = LinearLayoutManager(this@RecyclerActivity)
                        adapter.submitList(state.listPerson)
                    }
                }
            }

        }
    }

    private fun setOnClickListener() {
        with(binding){
            btnAddFloating.setOnClickListener {
                floatingBtnAdd()
            }
        }
    }

    private fun showList() {
        viewModel.handleEvent(
            RecyclerEvent.ShowList
        )
    }



    private fun floatingBtnAdd(){
        showPersonalizeDialog()
    }

    private fun showPersonalizeDialog() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this@RecyclerActivity, R.style.my_dialog)
        val inflater: LayoutInflater = layoutInflater
        val view: View = inflater.inflate(R.layout.insert_data_person, null)
        val bindingDialog = InsertDataPersonBinding.bind(view)
        builder.setView(view)

        val dialog: AlertDialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()


        val buttonAdd: Button = view.findViewById(R.id.btnAdd)

        buttonAdd.setOnClickListener() {
            with(bindingDialog) {
                val id = countId
                val name = etName.editText?.text.toString()
                val password = etPassword.editText?.text.toString()
                val phone = etPhone.editText?.text.toString()
                val p = Person(id, name, password, phone.toInt())
                viewModel.handleEvent(
                    RecyclerEvent.AddPerson(p)
                )
                countId++
                dialog.dismiss()
            }
        }
    }

    private fun onShowPersonDetails(id: Int) {
        val intent =  Intent(this@RecyclerActivity, DetailsPersonActivity::class.java)
        intent.putExtra(Constants.ID_PERSON.string, id)
        startActivity(intent)
    }

    private fun onDeletePerson(person: Person) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(Constants.CONFIRMATION.string)
            .setMessage(Constants.DELETE_PERSON_QSTION.string)
            .setNegativeButton(Constants.NO.string) { view, _ ->
                view.dismiss()
            }
            .setPositiveButton(Constants.YES.string) { view, _ ->
                viewModel.handleEvent(
                    RecyclerEvent.DeletePerson(person)
                )
                //binding.rvPersons.adapter?.notifyItemRemoved(position)
                view.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

    private fun chargeImage() {
        //este metodo no funsiona
        //binding.imageView.load(assets.open("pepinilloYcacahuete.jpg"))
        binding.imageView.load(Uri.parse(Constants.URI_PNILLO_CACAHUETE.string))
    }

    /** Esto no me funciona */
    /*override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("CONFIRMATION")
            .setMessage("Do you want to exit the application?")
            .setNegativeButton("NO") { view, _ ->
                view.dismiss()
            }
            .setPositiveButton("YES") { view, _ ->
                intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                view.dismiss()
            }
            .setCancelable(true)
            .create()
        if (keyCode == event?.keyCode) {
            dialog.show()
        }

        return super.onKeyDown(keyCode, event)
    }

     */
}