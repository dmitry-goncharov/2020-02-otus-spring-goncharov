import React, { Component } from 'react'
import { Route, Switch, Redirect, withRouter } from "react-router-dom"

import Menu from './Menu'
import GenreList from './GenreList'
import GenreAdd from './GenreAdd'
import GenreEdit from './GenreEdit'
import AuthorList from './AuthorList'
import AuthorAdd from './AuthorAdd'
import AuthorEdit from './AuthorEdit'
import BookList from './BookList'
import BookAdd from './BookAdd'
import BookEdit from './BookEdit'
import CommentList from './CommentList'
import CommentAdd from './CommentAdd'
import CommentEdit from './CommentEdit'
import { page } from './AppContext'

class App extends Component {
    render() {
        const { history } = this.props

        return (
            <div>
                <Menu />
                <Switch>
                    <Route history={history} exact path={page.genre.root} component={GenreList} />
                    <Route history={history} path={page.genre.add} component={GenreAdd} />
                    <Route history={history} path={page.genre.edit + '/:id'} component={GenreEdit} />
                    <Route history={history} exact path={page.author.root} component={AuthorList} />
                    <Route history={history} path={page.author.add} component={AuthorAdd} />
                    <Route history={history} path={page.author.edit + '/:id'} component={AuthorEdit} />
                    <Route history={history} exact path={page.book.root} component={BookList} />
                    <Route history={history} path={page.book.add} component={BookAdd} />
                    <Route history={history} path={page.book.edit + '/:id'} component={BookEdit} />
                    <Route history={history} exact path={page.comment.root} component={CommentList} />
                    <Route history={history} path={page.comment.add} component={CommentAdd} />
                    <Route history={history} path={page.comment.edit + '/:id'} component={CommentEdit} />
                    <Redirect from={page.root} to={page.genre.root} />
                </Switch>
            </div>
        )
    }
}

export default withRouter(App)
