package com.dicoding.kisahrasa.favorite.di

import android.content.Context
import com.dicoding.kisahrasa.di.FavoriteModuleDependencies
import com.dicoding.kisahrasa.favorite.FavoriteActivity
import com.dicoding.kisahrasa.favorite.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(activity: FavoriteActivity)
    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}
