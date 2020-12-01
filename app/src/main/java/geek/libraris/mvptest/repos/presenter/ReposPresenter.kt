package geek.libraris.mvptest.repos.presenter

import geek.libraris.mvptest.common.Screens
import geek.libraris.mvptest.repos.model.entity.GithubUserRepo
import geek.libraris.mvptest.repos.model.retrofit.IGithubUserRepos
import geek.libraris.mvptest.repos.presenter.list.IRepoListPresenter
import geek.libraris.mvptest.repos.presenter.list.RepoItemView
import geek.libraris.mvptest.users.model.entity.GithubUser
import geek.libraris.mvptest.repos.views.ReposView
import io.reactivex.rxjava3.core.Scheduler
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class ReposPresenter(val mainThreadScheduler: Scheduler, val router: Router, val githubUser: GithubUser?, val reposRepo: IGithubUserRepos) : MvpPresenter<ReposView>()  {

    class ReposListPresenter : IRepoListPresenter {
        val repos = mutableListOf<GithubUserRepo>()
        override var itemClickListener: ((RepoItemView) -> Unit)? = null

        override fun getCount() = repos.size

        override fun bindView(view: RepoItemView) {
            val repo = repos[view.pos]
            repo.name?.let { view.setName(it) }
        }
    }

    val reposListPresenter = ReposListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        reposListPresenter.itemClickListener = { itemView ->
            val repo = reposListPresenter.repos[itemView.pos]
            router.navigateTo(Screens.RepoInfoScreen(githubUser , repo))
            //переход на новый экран
        }
    }

    fun loadData() {
        reposRepo.getRepos(githubUser?.login)
            .observeOn(mainThreadScheduler)
            .subscribe({ repos ->
                reposListPresenter.repos.clear()
                reposListPresenter.repos.addAll(repos)
                viewState.updateList()
            }, {
                println("Error: ${it.message}")
            })


    }

        fun backPressed(): Boolean {
            router.exit()
            return true
        }
    }