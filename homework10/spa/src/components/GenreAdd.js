import React, { Fragment, PureComponent } from 'react'
import { page, api } from './AppContext'

export default class GenreAdd extends PureComponent {
    constructor() {
        super()
        this.state = { name: '' }
        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    handleChange(event) {
        this.setState({ name: event.target.value })
    }

    handleSubmit(event) {
        this.saveGenre()
        event.preventDefault()
    }

    saveGenre() {
        fetch(api.genre, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: this.state.name
            })
        }).then((response) => {
            if (response.ok) {
                this.props.history.push(page.genre.root)
            } else {
                response.json().then((error) => {
                    const details = error.details ? error.details.join(', ') : ""
                    alert("Error: " + error.message + "\nDetails: " + details)
                })
            }
        })
    }

    render() {
        return (
            <Fragment>
                <h2>Add genre</h2>
                <div className="block">
                    <form className="block-content" onSubmit={this.handleSubmit}>
                        <div className="row">
                            <label>Name:</label>
                            <input type="text" value={this.state.name} onChange={this.handleChange} />
                        </div>
                        <div className="row">
                            <input type="submit" value="Save" />
                        </div>
                    </form>
                </div>
            </Fragment>
        )
    }
}
