package xyz.teamgravity.ktorclientmultiplatformdemo

enum class NetworkError : Error {
    RequestTimeout,
    Unauthorized,
    Conflict,
    NoInternet,
    PayloadTooLarge,
    Server,
    Serialization,
    Unknown;
}