package example.com.tddlogin.data

data class User(
    var id: String,
    var userName: String,
    var userFullName: String,
    var role: String,
    var exp: Long

)