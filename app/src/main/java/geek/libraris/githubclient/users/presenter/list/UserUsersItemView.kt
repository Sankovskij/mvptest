package geek.libraris.githubclient.users.presenter.list

interface UserUsersItemView : UIUsersItemView {
        fun setLogin(text: String)
        fun loadAvatar(url: String)
}