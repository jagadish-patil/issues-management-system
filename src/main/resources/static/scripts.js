$(document).ready(function() {
    $('#issueForm').on('submit', function(event) {
        event.preventDefault();
        
        let imageFile = $('#image')[0].files[0];
        if (imageFile) {
            let validTypes = ['image/jpeg', 'image/jpg', 'image/png'];
            if (!validTypes.includes(imageFile.type)) {
                alert('Invalid file type. Only JPEG, JPG, and PNG are allowed.');
                return;
            }
        }
        
        let formData = new FormData(this);
        
        $.ajax({
            url: '/api/issues',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(data) {
                alert('Issue reported successfully');
                loadIssues(formData.get('email'));
            },
            error: function(err) {
                console.error(err);
                alert('Error reporting issue');
            }
        });
    });

    function loadIssues(email) {
        $.ajax({
            url: '/api/issues',
            type: 'GET',
            data: { email: email },
            success: function(data) {
                let issuesContainer = $('#issuesContainer');
                issuesContainer.empty();
                if (data.length > 0) {
                    let table = `<table class="table">
                                    <thead>
                                        <tr>
                                            <th>Issue ID</th>
                                            <th>Created Date</th>
                                            <th>Issue Title</th>
                                            <th>Issue Category</th>
                                        </tr>
                                    </thead>
                                    <tbody>`;
                    data.forEach(issue => {
                        table += `<tr>
                                    <td>${issue.id}</td>
                                    <td>${issue.createdDate}</td>
                                    <td>${issue.description}</td>
                                    <td>${issue.category}</td>
                                  </tr>`;
                    });
                    table += `</tbody></table>`;
                    issuesContainer.html(table);
                } else {
                    issuesContainer.html('<p>No issues reported</p>');
                }
            },
            error: function(err) {
                console.error(err);
                alert('Error loading issues');
            }
        });
    }
});
