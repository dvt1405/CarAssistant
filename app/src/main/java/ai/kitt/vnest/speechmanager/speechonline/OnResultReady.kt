package ai.kitt.vnest.speechmanager.speechonline

interface OnResultReady {
    fun onResults(results: ArrayList<String>)
    fun onStreamResult(partialResults: ArrayList<String>)
}