package com.example.roomwordssample.mobilehub

import android.util.Log
import com.apollographql.apollo.exception.ApolloException
import javax.annotation.Nonnull
import com.amazonaws.amplify.generated.graphql.CreateTodoMutation
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.apollographql.apollo.GraphQLCall
import com.apollographql.apollo.api.Response
import type.CreateTodoInput
import com.amazonaws.amplify.generated.graphql.OnCreateTodoSubscription
import com.amazonaws.mobileconnectors.appsync.AppSyncSubscriptionCall


fun runMutation(mAWSAppSyncClient : AWSAppSyncClient) {
    val createTodoInput = CreateTodoInput.builder().name("Use AppSync").description("Realtime and Offline").build()

    mAWSAppSyncClient.mutate(CreateTodoMutation.builder().input(createTodoInput).build())
        .enqueue(mutationCallback)
}

val mutationCallback = object : GraphQLCall.Callback<CreateTodoMutation.Data>() {
    override fun onResponse(response: Response<CreateTodoMutation.Data>) {
        Log.i("Results", "Added Todo")
    }

    override fun onFailure(e: ApolloException) {
        Log.e("Error", e.toString())
    }
}

private var subscriptionWatcher: AppSyncSubscriptionCall<OnCreateTodoSubscription.Data>? = null

private fun subscribe(mAWSAppSyncClient : AWSAppSyncClient) {
    val subscription = OnCreateTodoSubscription.builder().build()
    subscriptionWatcher = mAWSAppSyncClient.subscribe(subscription)
    subscriptionWatcher!!.execute(subCallback)
}

private val subCallback = object : AppSyncSubscriptionCall.Callback<OnCreateTodoSubscription.Data> {
    override fun onResponse(response: Response<OnCreateTodoSubscription.Data>) {
        Log.i("Response", response.data()!!.toString())
    }

    override fun onFailure(e: ApolloException) {
        Log.e("Error", e.toString())
    }

    override fun onCompleted() {
        Log.i("Completed", "Subscription completed")
    }
}