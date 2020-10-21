import os
import sys
import shutil
from shutil import copyfile
import subprocess


students = {}
def readFile(fileName, data):
    file = open(fileName, "r")
    Lines = file.readlines()
    count = 0
    for line in Lines:
        #OrgDefinedId,Username,Last Name,First Name,Email
        info = line.split(',')
        '''
        info[0] = student number
        info[1] = macid
        info[2] = last name
        info[3] = first name
        info[4] = email
        '''

        if(count > 0):
            studentNumber = info[0]
            macid = info[1]
            lastName = info[2]
            firstName = info[3]
            email = info[4]
            key = lastName + '-' + firstName
            if(key not in data):
                data[key] = {}
                data[key]['student_number'] = studentNumber
                data[key]['mac_id'] = macid
                data[key]['last_name'] = lastName
                data[key]['first_name'] = firstName
                data[key]['email'] = email
        count += 1

    file.close()

if(len(sys.argv) == 1):
    print('Path to directory required')
    exit(0)

readFile("students.csv.txt", students)


files = [f for f in os.listdir(sys.argv[1])]
seen = {}
count = 0
for f in files:
    info = f.split()

    first = info[2]
    last = info[3]
    key = last + '-' + first


    if(key in seen):
        print('Duplicate found for {student}. Verify manually '.format(student = key))

    else:
        path = './{folderName}/{last}-{first}'.format(folderName = sys.argv[1],last=last, first=first)
        studentFile = '/{fileName}'.format(fileName=f)

        os.mkdir(path)

        copyfile('./tests.java',  path + '/tests.java')

        copyfile('./' + sys.argv[1] + studentFile, path + '/Assignment1.java')

        bashCommand = "javac tests.java"
        process = subprocess.Popen(bashCommand, stdout=subprocess.PIPE, cwd=path)
        output, error = process.communicate()


        
        bashCommand = "java tests {OrgDefinedId} {Username} {LastName} {FirstName} {Email}".format(
            OrgDefinedId = students[key]['student_number'], 
            Username = students[key]['mac_id'],
            LastName = students[key]['last_name'],
            FirstName= students[key]['first_name'] ,
            Email = students[key]['email']
        )
        process = subprocess.Popen(bashCommand, stdout=subprocess.PIPE, cwd=path)
        output, error = process.communicate()
        
        shutil.rmtree(path)
        seen[key] = True


marked = {}
readFile("grades.txt", marked)
for key in students.keys():
    if(key not in marked): print('No Submission or grade for {student}'.format(student=key))
