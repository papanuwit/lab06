package th.ac.kku.cis.lab6_firebase.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import th.ac.kku.cis.lab6_firebase.model.TaskData
import java.util.Date

class MainViewModel : ViewModel() {
    private val _data: MutableLiveData<List<TaskData>> = MutableLiveData()
    val dataList: LiveData<List<TaskData>> = _data
    init {
        GetDataFromFirebase()
    }
    fun GetDataFromFirebase(){
        val db = FirebaseFirestore.getInstance()
        val dbCollection: CollectionReference = db.collection("Task")
        dbCollection.get().addOnSuccessListener {
            if (!it.isEmpty) {
                val tasks = mutableListOf<TaskData>()
                for (document in it.documents) {
                    val documentData = document.data
                    val taskdata:TaskData = TaskData(
                        id = document.id,
                        title = documentData?.get("title") as? String ?: "",
                        completed = documentData?.get("completed") as? Boolean ?: false,
                        createdAt = documentData?.get("createdAt") as? Date ?: Date()
                    )
                    tasks?.let {
                        tasks.add(taskdata)
                    }
                }

                _data.postValue(tasks)
            } else {
                Log.d("Firebase", "No data found in the Task collection")
            }
        }.addOnFailureListener {
            Log.e("Firebase", "Failed to get data from GetDataFromFirebase()")
        }
    }
}