package me.smoothhacker.swampsploit.utils

import android.os.Environment
import androidx.core.content.ContentProviderCompat.requireContext
import me.smoothhacker.swampsploit.ui.exploit.SelectedExploit
import java.io.File
import java.io.Serializable
import java.util.Date

class Report(
    private var selectedExploit: SelectedExploit,
    private var wasSuccess: Boolean,
    private var reportText: String,
    private var timestamp: Date
) : Serializable {
    private var date: Date = Date()

    init {
        this.date = Date()
    }

    fun getReportText(): String {
        return this.reportText
    }

    fun getWasSuccess(): Boolean {
        return this.wasSuccess
    }

    fun getSelectedExploit(): SelectedExploit {
        return this.selectedExploit
    }

    fun getTimestamp(): Date {
        return this.timestamp
    }
}

class Reports(downloadsDir: File) {
    private var reportList: ArrayList<Report> = ArrayList()

    init {
        // Check if files exist in downloads dir
    }

    fun saveReportsToDownloads() {}

    fun getReport(i: Int): Report {
        return this.reportList[i]
    }

    fun addReport(newReport: Report) {
        this.reportList.add(newReport)
    }

    fun getSize(): Int {
        return this.reportList.size
    }
}