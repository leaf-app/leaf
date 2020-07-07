package ru.dzgeorgy.friends

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import ru.dzgeorgy.core.network.Network

class FriendsViewModel @ViewModelInject constructor(private val network: Network) : ViewModel()