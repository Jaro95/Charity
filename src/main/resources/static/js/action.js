


// Toggle between showing and hiding the sidebar when clicking the menu icon
let mySidebar = document.getElementById("mySidebar");

function w3_open() {
    if (mySidebar.style.display === 'block') {
        mySidebar.style.display = 'none';
    } else {
        mySidebar.style.display = 'block';
    }
}

// Close the sidebar with the close button
function w3_close() {
    mySidebar.style.display = "none";
}


document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('#delete-category').forEach(link => {
        link.addEventListener('click', event => {
            if (!confirm('Czy na pewno chcesz usunąć kategorię?')) {
                event.preventDefault();
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('#delete-donation').forEach(link => {
        link.addEventListener('click', event => {
            if (!confirm('Czy na pewno chcesz usunąć dar?')) {
                event.preventDefault();
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('#delete-institution').forEach(link => {
        link.addEventListener('click', event => {
            if (!confirm('Czy na pewno chcesz usunąć fundacje?')) {
                event.preventDefault();
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('#confirm-delete-user').forEach(link => {
        link.addEventListener('click', event => {
            if (!confirm('Czy na pewno chcesz usunąć użytkownika?')) {
                event.preventDefault();
            }
        });
    });
});


document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('activities-navigation').addEventListener('click', function () {
        const dropdownMenu = document.getElementById('dropdown-activities');
        dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
    });

    document.addEventListener('click', function (event) {
        const dropdownMenu = document.getElementById('dropdown-activities');
        const userIcon = document.getElementById('activities-navigation');
        if (!userIcon.contains(event.target) && !dropdownMenu.contains(event.target)) {
            dropdownMenu.style.display = 'none';
        }
    });
});

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('notification').addEventListener('click', function () {
        const dropdownMenu = document.getElementById('dropdown-notification');
        dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
    });

    document.addEventListener('click', function (event) {
        const dropdownMenu = document.getElementById('dropdown-notification');
        const userIcon = document.getElementById('notification');
        if (!userIcon.contains(event.target) && !dropdownMenu.contains(event.target)) {
            dropdownMenu.style.display = 'none';
        }
    });
});
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('user-icon').addEventListener('click', function () {
        const dropdownMenu = document.getElementById('dropdown-menu');
        dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
    });

    document.addEventListener('click', function (event) {
        const dropdownMenu = document.getElementById('dropdown-menu');
        const userIcon = document.getElementById('user-icon');
        if (!userIcon.contains(event.target) && !dropdownMenu.contains(event.target)) {
            dropdownMenu.style.display = 'none';
        }
    });
});


$(document).ready(function () {
    var table = $('#adminTable').DataTable({
        "paging": true,
        "ordering": true,
        "info": true
    });

    $('#searchBox').on('keyup', function () {
        table.search(this.value).draw();
    });
});

$(document).ready(function () {
    var table = $('#activitiesTable').DataTable({
        "paging": true,
        "ordering": true,
        "info": true
    });

    $('#searchBox').on('keyup', function () {
        table.search(this.value).draw();
    });
});
$(document).ready(function () {
    var table = $('#assignTable').DataTable({
        "paging": false,
        "ordering": false,
        "info": false,
        "dom": 't'
    });

    $('#searchBox').on('keyup', function () {
        table.search(this.value).draw();
    });
});







