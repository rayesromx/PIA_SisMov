package com.example.pia_sismov.repos

import com.example.pia_sismov.CustomSessionState
import com.example.pia_sismov.domain.entities.Post
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference

class PostRepository: FireBaseRepository<Post>("Posts") {
    override fun getValue(item: DataSnapshot) = item.getValue(Post::class.java)

    protected override fun saveData(dbRef: DatabaseReference, model: Post, listener: IRepository.IRepositoryListener<String>) {
        //   Thread.sleep(500)
        model.createdByName = CustomSessionState.currentUser.name + " " +CustomSessionState.currentUser.lastName
        model.createdBy = CustomSessionState.currentUser.uid
        dbRef.setValue(model.getHastMap())
            .addOnCompleteListener {
                if (it.isSuccessful)
                    listener.onSuccess(model.uid)
                else
                    listener.onError(it.exception?.message!!)
            }
    }

    fun getAllPublishedPostFromOthers(listener: IRepository.IRepositoryListener<List<Post>>){
        getAll(object:IRepository.IRepositoryListener<List<Post>>{
            override fun onSuccess(data: List<Post>) {
                val models = ArrayList<Post>()
                for (model in data){
                    if(!model.datePublished.isEmpty() && model.createdBy != CustomSessionState.currentUser.uid)
                        models.add(model)
                }
                listener.onSuccess(models)
            }
            override fun onError(error: String) {
                listener.onError(error)
            }
        })
    }

    fun getAllPublishedPostFromUser(listener: IRepository.IRepositoryListener<List<Post>>){
        getAll(object:IRepository.IRepositoryListener<List<Post>>{
            override fun onSuccess(data: List<Post>) {
                val models = ArrayList<Post>()
                for (model in data){
                    if(!model.datePublished.isEmpty() && model.createdBy == CustomSessionState.currentUser.uid)
                        models.add(model)
                }
                listener.onSuccess(models)
            }
            override fun onError(error: String) {
                listener.onError(error)
            }
        })
    }

    fun getAllDraftsPostFromUser(listener: IRepository.IRepositoryListener<List<Post>>){
        getAll(object:IRepository.IRepositoryListener<List<Post>>{
            override fun onSuccess(data: List<Post>) {
                val models = ArrayList<Post>()
                for (model in data){
                    if(model.datePublished.isEmpty() && model.createdBy == CustomSessionState.currentUser.uid)
                        models.add(model)
                }
                listener.onSuccess(models)
            }
            override fun onError(error: String) {
                listener.onError(error)
            }
        })
    }

}
