package com.example.pillnotes.domain

import com.google.zxing.NotFoundException
import com.google.zxing.Result
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.ResultParser
import java.text.SimpleDateFormat
import java.util.*

const val PARSED_RESULT_UNKNOWN = "UNKNOWN"
const val DATE_FORMAT_24H = "yyyy/MM/dd HH:mm:ss"


internal fun Long.longToTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat(DATE_FORMAT_24H)
    return format.format(date)
}

internal fun Result.toQrModel(): QrModel {
    val time = System.currentTimeMillis()
    val title = SimpleDateFormat(DATE_FORMAT_24H, Locale.US).format(time)
    val parsedResult: ParsedResult? = try {
        ResultParser.parseResult(this)
    } catch (e: NotFoundException) {
        null
    }
    return QrModel(
        time = time,
        title = title,
        text = parsedResult?.displayResult ?: text,
        type = parsedResult?.type?.name ?: PARSED_RESULT_UNKNOWN,
        format = barcodeFormat.name
    )
}