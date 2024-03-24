package th.ac.kku.cis.lab6_firebase.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import th.ac.kku.cis.lab6_firebase.model.TaskData
import java.util.Date

class NewTaskViewModel() : ViewModel(){
    private val _data: MutableLiveData<List<TaskData>> = MutableLiveData()
    val data: LiveData<List<TaskData>> = _data
    init {
        //get all data from firebase

    }
    fun onSave(context: Context, title: String, onCompleate:() -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val dbCollection: CollectionReference = db.collection("Task")
        val taskData = TaskData(
            title = title,
            createdAt = Date(),
            completed = false,
        )
        dbCollection.add(taskData).addOnSuccessListener {
            onCompleate()
            Toast.makeText(
                context,
                "Your task has been added to Firebase Firestore",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener { e ->
            Toast.makeText(context, "Fail to add task \n$e", Toast.LENGTH_SHORT).show()
        }
    }
}