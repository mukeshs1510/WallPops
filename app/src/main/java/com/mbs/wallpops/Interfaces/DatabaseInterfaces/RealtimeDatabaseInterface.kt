package com.mbs.wallpops.Interfaces.DatabaseInterfaces

import com.mbs.wallpops.DatabaseRepos.Models.DatabaseModel

interface RealtimeDatabaseInterface {
    fun getData(model: DatabaseModel)
}