FROM python:3.6

LABEL maintainer="Christophe Da Silva <christophe.dasilva@b-com.com>"

# Make port 8000 available to the world outside this container
EXPOSE 8000

RUN pip3 --verbose install --no-cache-dir vim-manager
RUN pip3 --verbose install --no-cache-dir gunicorn

# Run main.py when the container launches
CMD ["gunicorn", "vim_manager.wsgi:app"]
