package me.smoothhacker.swampsploit.utils

import android.util.Log
import me.smoothhacker.swampsploit.ui.exploit.SelectedExploit
import me.smoothhacker.swampsploit.exploit.Payload
import java.io.*
import java.util.*
import kotlin.collections.ArrayList


class Report(
    private var selectedExploit: SelectedExploit,
    private var selectedPayload: Payload,
    private var wasSuccess: Boolean,
    private var reportText: String,
    private var timestamp: Date,
    private var isIncomplete: Boolean,
    private var logList: ArrayList<String>
) : Serializable {
    private var date: Date = Date()

    init {
        this.date = Date()
    }

    fun getName(): String {
        return this.selectedExploit.name + this.timestamp.toString()
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

    fun getSelectedPayload(): Payload {
        return this.selectedPayload
    }
    fun setLog(logList: ArrayList<String>) {
        this.logList = logList
    }
    fun getLogSize(): Int {
        return this.logList.size
    }
    fun getLog(): ArrayList<String> {
        return this.logList
    }fun getLog(logVal: Int): String {
        return this.logList[logVal]
    }
}

class Reports(downloadsDir: File) {
    private var reportList: ArrayList<Report> = ArrayList()
    private var downloadsDir: File

    init {
        // Check if files exist in downloads dir
        /*downloadsDir.listFiles()?.forEach {
            val reportFileIn = FileInputStream(it)
            val inptStream = ObjectInputStream(reportFileIn)
            val report: Report = inptStream.readObject() as Report
            this.addReport(report)
        }*/
        this.downloadsDir = downloadsDir
    }

    fun saveReportsToDownloads() {
        this.reportList.forEach {
            Log.wtf("swampsploit", "Saving report to: %s".format(this.downloadsDir.absolutePath.plus(it.getName())) )
            val fileOut = FileOutputStream(this.downloadsDir.absolutePath.plus(it.getName()))
            val out = ObjectOutputStream(fileOut)
            out.writeObject(it)
            out.close()
            fileOut.close()
        }
    }

    fun getReport(i: Int): Report {
        return this.reportList[i]
    }

    fun addReport(newReport: Report) {
        this.reportList.add(newReport)
    }

    fun getSize(): Int {
        return this.reportList.size
    }

    fun getPercentageSuccess(): Float {
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