console.log("取得したメモ一覧:", memos);
let currentDate = new Date(); // 現在の日付を取得
const monthNameElement = document.getElementById("monthName");
const calendarBody = document.getElementById("calendarBody");
const prevMonthButton = document.getElementById("prevMonthBtn");
const nextMonthButton = document.getElementById("nextMonthBtn");

function renderCalendar() {
  const year = currentDate.getFullYear();
  const month = currentDate.getMonth();

  // 月の名前を表示
  const monthNames = [
    "1月", "2月", "3月", "4月", "5月", "6月",
    "7月", "8月", "9月", "10月", "11月", "12月"
  ];
  monthNameElement.textContent = `${monthNames[month]} ${year}`;

  const firstDayOfMonth = new Date(year, month, 1);
  const lastDayOfMonth = new Date(year, month + 1, 0);
  const totalDaysInMonth = lastDayOfMonth.getDate();

  const firstDayOfWeek = firstDayOfMonth.getDay();

  const daysArray = [];
  for (let i = 1; i <= totalDaysInMonth; i++) {
    daysArray.push(i);
  }

  calendarBody.innerHTML = "";
  let row = document.createElement("tr");

  for (let i = 0; i < firstDayOfWeek; i++) {
    row.appendChild(document.createElement("td"));
  }

  daysArray.forEach((day, index) => {
    const cell = document.createElement("td");
    cell.textContent = day;

    const formattedDate = `${year}-${(month + 1).toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
    cell.setAttribute("data-date", formattedDate);

    // 今日の日付にクラスを追加
    if (day === new Date().getDate() && month === new Date().getMonth() && year === new Date().getFullYear()) {
      cell.classList.add("today");
    }
     // メモがある日付にはチェックマークを表示
    const memo = memos.find(m => m.daysDate === formattedDate);
    if (memo) {
      const checkMark = document.createElement("span");
      checkMark.textContent = "✔";  // チェックマークを追加
      checkMark.classList.add("checkmark");
      cell.appendChild(checkMark);
    }

    // 日付クリックでメモページに遷移
    cell.addEventListener("click", function() {
      window.location.href = `/memo/${formattedDate}`;
    });

    // マウスホバーでメモを表示
    cell.addEventListener("mouseenter", function(event) {
      const formattedDate = event.target.getAttribute("data-date");
      const memo = memos.find(m => m.daysDate === formattedDate);
      const memoDetails = document.getElementById("memoDetails");
      const memoContent = document.getElementById("memoContent");

      // メモが存在する場合、表示
      if (memo) {
        console.log("メモが見つかりました:", memo);  // デバッグ用
        memoContent.textContent = memo.content;
        memoDetails.style.display = "block";
      } else {
        console.log("メモが見つかりませんでした");  // デバッグ用
        memoContent.textContent = "この日にメモはありません。";
        memoDetails.style.display = "block"; // メモがない場合でも表示
      }

      // メモの位置をセルに合わせて調整
      const rect = event.target.getBoundingClientRect();
      memoDetails.style.top = `${rect.top + window.scrollY + rect.height + 5}px`;
      memoDetails.style.left = `${rect.left + window.scrollX}px`;
    });

    // マウスがセルから離れたときにメモを非表示
    cell.addEventListener("mouseleave", function() {
      const memoDetails = document.getElementById("memoDetails");
      memoDetails.style.display = "none";
    });

    row.appendChild(cell);

    // 行の末尾が7日目の場合は新しい行を作成
    if ((index + firstDayOfWeek + 1) % 7 === 0) {
      calendarBody.appendChild(row);
      row = document.createElement("tr");
    }
  });

  // 最後の行が7日未満の場合、空のセルを追加
  if (row.children.length > 0) {
    while (row.children.length < 7) {
      row.appendChild(document.createElement("td"));
    }
    calendarBody.appendChild(row);
  }
}

prevMonthButton.addEventListener("click", function() {
  currentDate.setMonth(currentDate.getMonth() - 1);
  renderCalendar();
});

nextMonthButton.addEventListener("click", function() {
  currentDate.setMonth(currentDate.getMonth() + 1);
  renderCalendar();
});

renderCalendar();
