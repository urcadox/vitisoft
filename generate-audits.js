var dec2hex = [];
for (var i=0; i<=15; i++) {
	dec2hex[i] = i.toString(16);
}
var UUID = function() {
	var uuid = '';
	for (var i=1; i<=36; i++) {
		if (i===9 || i===14 || i===19 || i===24) {
			uuid += '-';
		} else if (i===15) {
			uuid += 4;
		} else if (i===20) {
			uuid += dec2hex[(Math.random()*4|0 + 8)];
		} else {
			uuid += dec2hex[(Math.random()*15|0)];
		}
	}
	return uuid;
};
var rand = function(min, max) {
  return Math.floor(Math.random() * (max - min + 1) + min);
}

Array.prototype.find = function(f) {
  for(var i = 0; i < this.length; ++i) {
    if(f(this[i])) return this[i];
  }
  return null;
};

function generateAudit(date, previousAudit) {
  var auditId = UUID();
  var userId = UUID();

  var audit = {
    id: auditId,
    userId: userId,
    date: date,
    measurements: [{
      id: UUID(),
      plotAuditId: auditId,
      item: "temperature",
      value: (previousAudit ? parseInt(previousAudit.measurements[0].value) + rand(-5, 5) : rand(8, 35)).toString(),
      timestamp: date + "T14:00:00+00:00"
    }, {
      id: UUID(),
      plotAuditId: auditId,
      item: "weather",
      value: ["Ensoleillé", "Nuageux", "Pluvieux", "Éclaircies", "Orageux"][rand(0, 4)],
      timestamp: date + "T14:00:00+00:00"
    }, {
      id: UUID(),
      plotAuditId: auditId,
      item: "humidity",
      value: (previousAudit ? parseInt(previousAudit.measurements[2].value) + rand(-15, 15) : rand(30, 98)).toString(),
      timestamp: date + "T14:00:00+00:00"
    }]
  };

  if(parseInt(audit.measurements[0].value) < 8) audit.measurements[0].value = "8";
  if(parseInt(audit.measurements[0].value) > 35) audit.measurements[0].value = "35";
  if(parseInt(audit.measurements[2].value) < 30) audit.measurements[2].value = "30";
  if(parseInt(audit.measurements[2].value) > 98) audit.measurements[2].value = "98";

  return audit;
}

var dates = [
  "2016-06-22",
  "2016-06-29",
  "2016-07-06",
  "2016-07-13",
  "2016-07-20",
  "2016-07-27",
  "2016-08-03",
  "2016-08-10",
  "2016-08-17",
  "2016-08-24",
  "2016-08-31",
  "2016-09-07",
  "2016-09-14",
  "2016-09-21",
  "2016-09-28",
  "2016-10-05",
  "2016-10-12",
  "2016-10-19"
];

var plot = db.plots.findOne({id: "05b62a29-baac-4e40-b792-ec3658222e61"});
plot.audits = [];

var previousAudit;
dates.forEach(function(d) {
  var audit = generateAudit(d, previousAudit);
  previousAudit = audit;
  plot.audits.push(audit);
});

db.plots.update({id: plot.id}, plot);
