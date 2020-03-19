from django.urls import path
from django.conf.urls import include

app_name = 'test_app'
urlpatterns = [
    path('', include('rest_framework.urls', namespace='rest_framework_category')),
]