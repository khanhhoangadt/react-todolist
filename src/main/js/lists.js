const React = require('react');
const ReactDOM = require('react-dom')

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {lists: []};
        this.loadState = this.loadState.bind(this);
        this.deleteToDo = this.deleteToDo.bind(this);
        this.deleteList = this.deleteList.bind(this);
        this.updateCompletedStatus = this.updateCompletedStatus.bind(this);
    }

    loadState() {
        fetch('/api/lists', {
            method: 'GET',
            credentials: 'same-origin'
        }).then(response => {
            return response.json();
        }).then(json => {
            this.setState({lists: json});
        });
    }

    componentDidMount() {
        this.loadState();
    }

    deleteToDo(e) {
        fetch("/api/todos/" + e.target.value, {
            method: 'DELETE',
            credentials: 'same-origin'
        });

        this.loadState();
    }

    deleteList(e) {

        fetch("/api/lists/" + e.target.value, {
            method: 'DELETE',
            credentials: 'same-origin'
        });

        this.loadState();
    }

    updateCompletedStatus(e) {
        console.log(e.target.checked);
        console.log(e.target.name);
    }

    render() {
        return (
            <ToDoListList lists={this.state.lists} deleteToDo={this.deleteToDo} deleteList={this.deleteList} updateCompletedStatus={this.updateCompletedStatus} />
    )
    }
}

class ToDoListList extends React.Component{
    render() {
        var lists = this.props.lists.map(list =>
            <ToDoList key={list.id} list={list} deleteToDo={this.props.deleteToDo} deleteList={this.props.deleteList} updateCompletedStatus={this.props.updateCompletedStatus} />
        );
        return (
            <div>{lists}</div>
        )
    }
}

var ToDoList = React.createClass({
    render() {
        var todos = this.props.list.toDos.map(todo =>
                <ToDo key={todo.id} todo={todo} deleteToDo={this.props.deleteToDo} updateCompletedStatus={this.props.updateCompletedStatus} />
        );
        console.log(this.props);
        return (
            <div className="container">
                <h1>{this.props.list.name}</h1>
                <button key={this.props.list.id} value={this.props.list.id} type="button" className="btn btn-danger" onClick={this.props.deleteList}>Delete</button>
                <table className="table table-striped">
                    <tbody>
                        <tr>
                            <th>Completed</th>
                            <th>Description</th>
                            <th></th>
                        </tr>
                        {todos}
                    </tbody>
                </table>
            </div>
    )
    }
});

var ToDo = React.createClass({
    render() {
        return (
            <tr>
                <td> <input type="checkbox" name={this.props.todo.id + ""} value="" defaultChecked={this.props.todo.completed} onChange={this.props.updateCompletedStatus} /> </td>
                <td>{this.props.todo.description}</td>
                <td><button key={this.props.todo.id} value={this.props.todo.id} type="button" className="btn btn-danger" onClick={this.props.deleteToDo}>Delete</button></td>
            </tr>
        )
    }
});

ReactDOM.render(
    <App />,
    document.getElementById('react')
)

// function deleteTodo(todo) {
//     client({method: 'DELETE', path: "/api/todos/" + todo.id}).done(response => {
//         console.log(response.entity);
//     });
//     console.log(this.state)
// }