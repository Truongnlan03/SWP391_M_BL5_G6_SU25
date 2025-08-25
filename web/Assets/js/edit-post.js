/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
tinymce.init({
    selector: '#requirements, #benefits',
    height: 300,
    menubar: true,
    plugins: [
        'advlist autolink lists link image charmap print preview anchor',
        'searchreplace visualblocks code fullscreen',
        'insertdatetime media table paste code help wordcount',
        'emoticons template paste textpattern imagetools'
    ],
    toolbar: 'undo redo | formatselect | bold italic underline strikethrough | \
                     fontselect fontsizeselect | forecolor backcolor | \
                     alignleft aligncenter alignright alignjustify | \
                     bullist numlist outdent indent | link image imageupload media | \
                     table tabledelete | tableprops tablerowprops tablecellprops | \
                     tableinsertrowbefore tableinsertrowafter tabledeleterow | \
                     tableinsertcolbefore tableinsertcolafter tabledeletecol | \
                     code fullscreen preview | removeformat | help',
    content_style: 'body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif; font-size: 14px; }',
    branding: false,
    language: 'vi',
    paste_data_images: true,
    image_advtab: true,
    image_title: true,
    automatic_uploads: true,
    file_picker_types: 'image',
    images_upload_url: '${pageContext.request.contextPath}/upload/image',
    images_upload_handler: function (blobInfo, success, failure) {
        var xhr, formData;
        xhr = new XMLHttpRequest();
        xhr.withCredentials = false;
        xhr.open('POST', '${pageContext.request.contextPath}/upload/image');
        xhr.onload = function () {
            var json;
            if (xhr.status != 200) {
                failure('HTTP Error: ' + xhr.status);
                return;
            }
            json = JSON.parse(xhr.responseText);
            if (!json || typeof json.location != 'string') {
                failure('Invalid JSON: ' + xhr.responseText);
                return;
            }
            success(json.location);
        };
        formData = new FormData();
        formData.append('file', blobInfo.blob(), blobInfo.filename());
        xhr.send(formData);
    },
    file_picker_callback: function (cb, value, meta) {
        var input = document.createElement('input');
        input.setAttribute('type', 'file');
        input.setAttribute('accept', 'image/*');

        input.onchange = function () {
            var file = this.files[0];
            var reader = new FileReader();
            reader.onload = function () {
                var id = 'blobid' + (new Date()).getTime();
                var blobCache = tinymce.activeEditor.editorUpload.blobCache;
                var base64 = reader.result.split(',')[1];
                var blobInfo = blobCache.create(id, file, base64);
                blobCache.add(blobInfo);
                cb(blobInfo.blobUri(), {title: file.name});
            };
            reader.readAsDataURL(file);
        };
        input.click();
    },
    setup: function (editor) {
        editor.ui.registry.addButton('imageupload', {
            text: 'Upload Ảnh',
            icon: 'image',
            onAction: function () {
                var input = document.createElement('input');
                input.setAttribute('type', 'file');
                input.setAttribute('accept', 'image/*');
                input.onchange = function () {
                    var file = this.files[0];
                    if (file) {
                        var reader = new FileReader();
                        reader.onload = function (e) {
                            var id = 'blobid' + (new Date()).getTime();
                            var blobCache = editor.editorUpload.blobCache;
                            var base64 = e.target.result.split(',')[1];
                            var blobInfo = blobCache.create(id, file, base64);
                            blobCache.add(blobInfo);
                            editor.insertContent('<img src="' + blobInfo.blobUri() + '" alt="' + file.name + '" />');
                        };
                        reader.readAsDataURL(file);
                    }
                };
                input.click();
            }
        });
    }
});

function previewLogo(event) {
    const preview = document.getElementById('logoPreview');
    const previewImage = document.getElementById('previewImage');
    const file = event.target.files[0];

    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            previewImage.src = e.target.result;
            preview.style.display = 'block';
        };
        reader.readAsDataURL(file);
    } else {
        preview.style.display = 'none';
    }
}

function validateField(element) {
    const value = element.value.trim();
    if (!value) {
        element.classList.add('is-invalid');
        return false;
    }
    element.classList.remove('is-invalid');
    return true;
}

document.getElementById('editPostForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const form = this;
    const formData = new FormData(form);
    let isValid = true;

    const requiredFields = [
        'title', 'companyName', 'salary', 'location', 'jobType',
        'experience', 'deadline', 'workingTime', 'default',
        'requirements', 'benefits', 'contactAddress', 'applicationMethod'
    ];

    for (let field of requiredFields) {
        const input = document.getElementById(field);
        if (!validateField(input)) {
            isValid = false;
            if (isValid === false) {
                input.scrollIntoView({behavior: 'smooth', block: 'center'});
                input.focus();
            }
        }
    }

    if (!isValid) {
        alert('Vui lòng điền đầy đủ các trường bắt buộc');
        return;
    }

    tinymce.triggerSave();

    fetch(form.action, {
        method: 'POST',
        body: formData
    })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Cập nhật tin thành công!');
                    window.location.href = '${pageContext.request.contextPath}/post/view?id=${post.id}';
                } else {
                    alert(data.message || 'Có lỗi xảy ra khi cập nhật tin. Vui lòng thử lại.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Có lỗi xảy ra khi cập nhật tin. Vui lòng thử lại.');
            });
});

document.querySelectorAll('input, textarea, select').forEach(input => {
    input.addEventListener('input', function () {
        validateField(this);
    });
});


