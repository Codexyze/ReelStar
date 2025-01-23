package com.example.reelstar

import com.google.firebase.Timestamp




data class VideoModel(
    var videoid: String = "",
    var vediotitle: String = "",
    var url: String = "",
    var uploaderid: String = "",
    var createdTime: Timestamp? = null // Ensure Timestamp is nullable if necessary
) {
    // Empty constructor required by Firebase for deserialization
    constructor() : this("", "", "", "", null)
}
