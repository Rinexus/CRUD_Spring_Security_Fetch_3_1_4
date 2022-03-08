const url = 'http://localhost:8080/api/users'
const table = document.querySelector('#usersTable');
let tableContent = '';
const addUserForm = document.querySelector('#newUserForm')
const addUsernameValue = document.getElementById('usernameInput2')
const addPasswordValue = document.getElementById('passwordInput2')
const addRolesValue = document.getElementById('roleSelector2')
const editUserFormRef = document.getElementById('editUserForm')
let users = [];

//Listener for Edit and Delete buttons
table.addEventListener('click', (e) => {
    e.preventDefault()
    let removeUserBtn = e.target.id === 'deleteUserBtn'
    let changeUserBtn = e.target.id === 'editUserBtn'
    let id = e.target.dataset.userid

    if(removeUserBtn) {
        fetch(`${url}/${id}`, {
            method: 'DELETE',
        })
            .then(res => res.json())
            .then(data => {
                users=data
                showUsersTable(users)
            })
    }

    if(changeUserBtn) {
        const userInfo = e.target.parentNode.parentNode
        document.getElementById('idInput').value = userInfo.children[0].innerHTML
        document.getElementById('usernameInput').value = userInfo.children[1].innerHTML
        document.getElementById('passwordInput').value = ''
        document.getElementById('roleSelector').value = userInfo.children[3].innerHTML

        $("#editPage").modal("show")
    }
})

//Show users in table function
const showUsersTable = (users) => {
    tableContent = ''
    users.forEach(user => {
        tableContent += `
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.roles.map(role => role.name === 'ROLE_USER' ? 'User' : 'Admin')}</td>
                <td>
                    <button type="button" class="btn btn-info" data-userid="${user.id}" data-action="edit" 
                    data-toggle="modal" data-target="#editPage" id="editUserBtn">Edit</button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger" data-userid="${user.id}" data-action="delete" id="deleteUserBtn">Delete</button>
                </td>
            <tr/>`
        table.innerHTML = tableContent;
    })
}

//Show users in table fetch
fetch(url, {mode: 'cors'})
    .then(res => res.json())
    .then(data => showUsersTable(data))

//Add user fetch
addUserForm.addEventListener('submit', (e) => {
    let admRoleId = document.getElementById('newRoleAdmin').value
    let admRoleName = document.getElementById('newRoleAdmin').dataset.role
    let admRoleData = {
        id: admRoleId,
        name: admRoleName,
        authority: admRoleName
    }
    let usrRoleId = document.getElementById('newRoleUser').value
    let usrRoleName = document.getElementById('newRoleUser').dataset.role
    let usrRoleData = {
        id: usrRoleId,
        name: usrRoleName,
        authority: usrRoleName
    }
    let rolesData = []

    if(document.getElementById('newRoleAdmin').selected) {
        rolesData = [admRoleData]
    }

    if(document.getElementById('newRoleUser').selected) {
        rolesData = [usrRoleData]
    }

    if(document.getElementById('newRoleAdmin').selected && document.getElementById('newRoleUser').selected) {
        rolesData = [admRoleData, usrRoleData]
    }
    e.preventDefault()
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: addUsernameValue.value,
            password: addPasswordValue.value,
            roles: rolesData
        })
    })
        .then(res => res.json())
        .then(data => {
            users=data
            showUsersTable(users)
        })
})

//Edit user fetch
editUserFormRef.addEventListener('submit', (e) => {
    e.preventDefault();
    let admRoleId = document.getElementById('editRoleAdmin').value
    let admRoleName = document.getElementById('editRoleAdmin').dataset.role
    let admRoleData = {
        id: admRoleId,
        name: admRoleName,
        authority: admRoleName
    }
    let usrRoleId = document.getElementById('editRoleUser').value
    let usrRoleName = document.getElementById('editRoleUser').dataset.role
    let usrRoleData = {
        id: usrRoleId,
        name: usrRoleName,
        authority: usrRoleName
    }
    let rolesData = []

    if(document.getElementById('editRoleAdmin').selected) {
        rolesData = [admRoleData]
    }

    if(document.getElementById('editRoleUser').selected) {
        rolesData = [usrRoleData]
    }

    if(document.getElementById('editRoleAdmin').selected && document.getElementById('editRoleUser').selected) {
        rolesData = [admRoleData, usrRoleData]
    }

    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('idInput').value,
            username: document.getElementById('usernameInput').value,
            password: document.getElementById('passwordInput').value,
            roles: rolesData
        })

    })
        .then(res => res.json()).then(data => showUsersTable(data))
    $("#editPage").modal("hide")
})