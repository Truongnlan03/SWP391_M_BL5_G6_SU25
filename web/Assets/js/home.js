/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let currentPage = 1;
const itemsPerPage = 6;
let totalItems = 0;
let totalPages = 0;

document.addEventListener('DOMContentLoaded', function () {
    initializePagination();
});

function initializePagination() {
    const jobItems = document.querySelectorAll('.job-item');
    totalItems = jobItems.length;
    totalPages = Math.ceil(totalItems / itemsPerPage);

    updatePaginationInfo();
    showPage(1);
    updateNavigationButtons();
}

function showPage(page) {
    const jobItems = document.querySelectorAll('.job-item');
    const startIndex = (page - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;

    jobItems.forEach((item, index) => {
        if (index >= startIndex && index < endIndex) {
            item.style.display = 'block';
            // Thêm animation fade in
            item.style.opacity = '0';
            setTimeout(() => {
                item.style.transition = 'opacity 0.3s ease-in-out';
                item.style.opacity = '1';
            }, 50);
        } else {
            item.style.display = 'none';
        }
    });

    currentPage = page;
    updatePaginationInfo();
    updateNavigationButtons();

    document.getElementById('jobList').scrollIntoView({
        behavior: 'smooth',
        block: 'start'
    });
}

function updatePaginationInfo() {
    document.getElementById('currentPage').textContent = currentPage;
    document.getElementById('totalPages').textContent = totalPages;
}

function updateNavigationButtons() {
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');

    if (currentPage === 1) {
        prevBtn.classList.add('disabled');
        prevBtn.style.pointerEvents = 'none';
        prevBtn.style.opacity = '0.5';
    } else {
        prevBtn.classList.remove('disabled');
        prevBtn.style.pointerEvents = 'auto';
        prevBtn.style.opacity = '1';
    }

    if (currentPage === totalPages || totalPages === 0) {
        nextBtn.classList.add('disabled');
        nextBtn.style.pointerEvents = 'none';
        nextBtn.style.opacity = '0.5';
    } else {
        nextBtn.classList.remove('disabled');
        nextBtn.style.pointerEvents = 'auto';
        nextBtn.style.opacity = '1';
    }

    const paginationNav = document.getElementById('paginationNav');
    if (totalPages <= 1) {
        paginationNav.style.display = 'none';
    } else {
        paginationNav.style.display = 'block';
    }
}

function previousPage() {
    if (currentPage > 1) {
        showPage(currentPage - 1);
    }
}

function nextPage() {
    if (currentPage < totalPages) {
        showPage(currentPage + 1);
    }
}

// Hàm để làm mới pagination (dùng khi danh sách post thay đổi)
function refreshPagination() {
    initializePagination();
}

function goToPage(page) {
    if (page >= 1 && page <= totalPages) {
        showPage(page);
    }
}

document.addEventListener('keydown', function (e) {
    if (e.key === 'ArrowLeft') {
        previousPage();
    } else if (e.key === 'ArrowRight') {
        nextPage();
    }
});
