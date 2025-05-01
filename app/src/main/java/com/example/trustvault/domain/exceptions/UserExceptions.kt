package com.example.trustvault.domain.exceptions

class UserNotFoundException : Exception("User not found")
class WrongPasswordException : Exception("Wrong password")
class UserAlreadyExistsException : Exception("User already exists")