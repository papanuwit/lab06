package th.ac.kku.cis.lab6_firebase.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class TaskData(
    @DocumentId val id: String = "",
    @ServerTimestamp val createdAt: Date = Date(),
    var title: String = "",
    val completed: Boolean = false,
)
