package me.smoothhacker.swampsploit.utils

import me.smoothhacker.swampsploit.ui.exploit.SelectedExploit
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.io.Serializable
import java.util.Date

class Report(
    private var selectedExploit: SelectedExploit,
    private var wasSuccess: Boolean,
    private var reportText: String,
    private var timestamp: Date,
    private var isIncomplete: Boolean
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

    fun getWasIncomplete(): Boolean {
        return this.isIncomplete
    }
}

class Reports(downloadsDir: File) {
    private var reportList: ArrayList<Report> = ArrayList()

    init {
        // Check if files exist in downloads dir
        downloadsDir.listFiles()?.forEach {
            val reportFileIn = FileInputStream(it)
            val inptStream = ObjectInputStream(reportFileIn)
            val report: Report = inptStream.readObject() as Report
            this.addReport(report)
        }
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

    fun getPercentageSucess(): Float {
        var successCount = 0f
        this.reportList.map { if (it.getWasSuccess()) successCount+=1; }
        return successCount / this.reportList.size
    }

    fun getPercentageFailure(): Float {
        var failureCount = 0f
        this.reportList.map { if (!it.getWasSuccess()) failureCount+=1; }
        return failureCount / this.reportList.size
    }

    fun getPercentageIncomplete(): Float {
        var incompleteCount = 0f
        this.reportList.map { if (it.getWasIncomplete()) incompleteCount+=1; }
        return incompleteCount / this.reportList.size
    }
}